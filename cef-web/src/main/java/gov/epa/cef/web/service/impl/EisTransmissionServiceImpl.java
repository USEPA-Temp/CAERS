package gov.epa.cef.web.service.impl;

import com.google.common.collect.Streams;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataListDto;
import gov.epa.cef.web.service.dto.EisDataReportDto;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import gov.epa.cef.web.service.dto.EisHeaderDto;
import gov.epa.cef.web.service.dto.EisSubmissionStatus;
import net.exchangenetwork.schema.header._2.ExchangeNetworkDocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EisTransmissionServiceImpl {

    private final EmissionsReportRepository reportRepository;

    private final EisXmlServiceImpl xmlService;

    @Autowired
    EisTransmissionServiceImpl(EisXmlServiceImpl xmlService,
                               EmissionsReportRepository reportRepository) {

        this.xmlService = xmlService;
        this.reportRepository = reportRepository;
    }

    public EisDataListDto retrieveDataList(Set<Long> emissionReports) {

        EisDataListDto result = new EisDataListDto();

        Streams.stream(this.reportRepository.findAllById(emissionReports))
            .map(new EisDataReportDto.FromEntity())
            .forEach(result);

        return result;
    }

    public EisDataStatsDto retrieveStatInfo(String agencyCode, Short year) {

        Collection<EisDataStatsDto.EisDataStatusStat> stats = this.reportRepository.findEisDataStatuses(agencyCode, year);

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
                .collect(Collectors.toList()));
    }

    public EisDataListDto submitReports(EisHeaderDto eisHeader) {

        EisDataListDto result = new EisDataListDto();

        String transactionId = transferXml(this.xmlService.generateEisDocument(eisHeader));

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

    private String transferXml(ExchangeNetworkDocumentType xml) {

        // TODO CEF-929

        return "{TBD CEF-929}".concat(UUID.randomUUID().toString());
    }
}
