package gov.epa.cef.web.service.impl;

import com.google.common.collect.Streams;

import gov.epa.cef.web.client.soap.NodeClient;
import gov.epa.cef.web.client.soap.NodeTransaction;
import gov.epa.cef.web.config.NetworkNodeName;
import gov.epa.cef.web.domain.EisTransactionHistory;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EisTransactionAttachmentRepository;
import gov.epa.cef.web.repository.EisTransactionHistoryRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataListDto;
import gov.epa.cef.web.service.dto.EisDataReportDto;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import gov.epa.cef.web.service.dto.EisTransactionHistoryDto;
import gov.epa.cef.web.service.mapper.EisTransactionMapper;
import gov.epa.cef.web.util.TempFile;
import net.exchangenetwork.schema.header._2.ExchangeNetworkDocumentType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EisTransmissionServiceImpl {

    private final EmissionsReportRepository reportRepository;

    private final EisTransactionHistoryRepository transactionHistoryRepo;

    private final EisTransactionAttachmentRepository attachmentRepo;

    private final EisXmlServiceImpl xmlService;

    private final EisTransactionMapper mapper;

    private final NodeClient nodeClient;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    EisTransmissionServiceImpl(EisXmlServiceImpl xmlService,
                               EmissionsReportRepository reportRepository,
                               EisTransactionHistoryRepository transactionHistoryRepo,
                               EisTransactionAttachmentRepository attachmentRepo,
                               EisTransactionMapper mapper,
                               NodeClient nodeClient) {

        this.xmlService = xmlService;
        this.reportRepository = reportRepository;
        this.transactionHistoryRepo = transactionHistoryRepo;
        this.attachmentRepo = attachmentRepo;
        this.mapper = mapper;
        this.nodeClient = nodeClient;
    }

    public EisDataListDto retrieveDataList(Set<Long> emissionReports) {

        EisDataListDto result = new EisDataListDto();

        Streams.stream(this.reportRepository.findAllById(emissionReports))
            .map(new EisDataReportDto.FromEntity())
            .forEach(result);

        return result;
    }

	public EisDataStatsDto retrieveStatInfoByYear(String agencyCode, Short year) {

        Collection<EisDataStatsDto.EisDataStatusStat> stats = this.reportRepository.findEisDataStatusesByYear(agencyCode, year);

        Collection<Integer> years = this.reportRepository.findEisDataYears(agencyCode);

        return new EisDataStatsDto()
            .withStatuses(stats)
            .withAvailableYears(years);
    }

    public EisDataListDto retrieveSubmittableData(EisDataCriteria criteria) {

        Collection<EmissionsReport> reports = criteria.getSubmissionStatus() == null
            ? this.reportRepository.findEisDataByYearAndNotComplete(criteria)
            : this.reportRepository.findEisDataByYearAndStatus(criteria);

        return new EisDataListDto(criteria)
            .withReports(reports.stream()
                .map(new EisDataReportDto.FromEntity())
                .map(report -> {

                    if (report.getLastTransactionId() != null) {
                        report.setAttachment(this.attachmentRepo.findByTransactionHistoryTransactionId(report.getLastTransactionId())
                            .map(attachment -> mapper.attachmentToDto(attachment))
                            .orElse(null));
                    }

                    return report;
                })
                .collect(Collectors.toList()));
    }

    public List<EisTransactionHistoryDto> retrieveTransactionHistory(String agencyCode) {

        return mapper.historyToDtoList(this.transactionHistoryRepo.findByAgencyCode(agencyCode));
    }

    public EisDataListDto submitReports(EisHeaderDto eisHeader) {

        EisDataListDto result = new EisDataListDto();

        String transactionId = transferXml(eisHeader);

        Streams.stream(this.reportRepository.findAllById(eisHeader.getEmissionsReports()))
            .peek(report -> {

                report.setEisLastTransactionId(transactionId);
                report.setEisLastSubmissionStatus(eisHeader.getSubmissionStatus());
                report.setEisPassed(false);

                this.reportRepository.save(report);
            })
            .map(new EisDataReportDto.FromEntity())
            .forEach(result);

        return result;
    }

    public EisDataReportDto updateReportComment(long reportId, String comment) {

        EmissionsReport report = this.reportRepository.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        report.setEisComments(comment);

        return new EisDataReportDto.FromEntity().apply(this.reportRepository.save(report));
    }
    
    public EisDataReportDto updateReportEisPassedStatus(long reportId, String passed) {

        EmissionsReport report = this.reportRepository.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        report.setEisPassed(Boolean.valueOf(passed));
        
        if (report.getEisLastSubmissionStatus() != null && report.getEisLastSubmissionStatus().equals(EisSubmissionStatus.ProdEmissions) && Boolean.valueOf(passed) == true) {
        	report.setEisLastSubmissionStatus(EisSubmissionStatus.Complete);
        } else if (report.getEisLastSubmissionStatus() != null && report.getEisLastSubmissionStatus().equals(EisSubmissionStatus.Complete) && Boolean.valueOf(passed) == false) {
        	report.setEisLastSubmissionStatus(EisSubmissionStatus.ProdEmissions);
        }

        return new EisDataReportDto.FromEntity().apply(this.reportRepository.save(report));
    }

    private String transferXml(EisHeaderDto eisHeader) {

        NodeTransaction transaction;

        ExchangeNetworkDocumentType xml = this.xmlService.generateEisDocument(eisHeader);

        try (TempFile tmpFile = TempFile.create("submission" + xml.getId(), ".zip")) {

            try (FileOutputStream fos = new FileOutputStream(tmpFile.getFile());
                 ZipOutputStream zos = new ZipOutputStream(fos)) {

                ZipEntry zipEntry = new ZipEntry("report.xml");
                zos.putNextEntry(zipEntry);

                this.xmlService.writeEisXmlTo(xml, zos);

                zos.closeEntry();
                zos.finish();

            } catch (FileNotFoundException e) {

                throw new IllegalStateException(e);
            } catch (IOException e) {

                throw new IllegalStateException(e);
            }

            transaction = this.nodeClient.submit(NetworkNodeName.eis, "submission" + xml.getId() + ".zip", tmpFile.getFile());

            logger.info(transaction.toString());

            EisTransactionHistory history = new EisTransactionHistory();
            history.setEisSubmissionStatus(eisHeader.getSubmissionStatus());
            history.setSubmitterName(eisHeader.getAuthorName());
            history.setTransactionId(transaction.getTransactionId());
            history.setAgencyCode(eisHeader.getAgencyCode());

            this.transactionHistoryRepo.save(history);

        }

        return transaction.getTransactionId();
    }
}
