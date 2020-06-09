package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.service.dto.EisDataCriteria;
import gov.epa.cef.web.service.dto.EisDataListDto;
import gov.epa.cef.web.service.dto.EisDataReportDto;
import gov.epa.cef.web.service.dto.EisDataStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EisTransmissionServiceImpl {

    private final EmissionsReportRepository reportRepository;

    @Autowired
    EisTransmissionServiceImpl(EmissionsReportRepository reportRepository) {

        this.reportRepository = reportRepository;
    }

    public EisDataStatsDto retrieveStatInfo(String agencyCode) {

        Collection<EisDataStatsDto.EisDataStatusStat> stats = this.reportRepository.findEisDataStatuses(agencyCode);

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

    public EisDataReportDto updateReportComment(long reportId, String comment) {

        EmissionsReport report = this.reportRepository.findById(reportId)
            .orElseThrow(() -> new NotExistException("Emissions Report", reportId));

        report.setEisComments(comment);

        return new EisDataReportDto.FromEntity().apply(this.reportRepository.save(report));
    }
}
