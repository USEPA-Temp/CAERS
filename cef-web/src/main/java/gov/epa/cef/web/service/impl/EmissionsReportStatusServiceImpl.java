package gov.epa.cef.web.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.ValidationStatus;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportIdRetriever;
import gov.epa.cef.web.service.EmissionsReportStatusService;
import gov.epa.cef.web.service.dto.EmissionsReportDto;
import gov.epa.cef.web.service.mapper.EmissionsReportMapper;
import gov.epa.cef.web.util.RepoLocator;

@Service
public class EmissionsReportStatusServiceImpl implements EmissionsReportStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmissionsReportStatusServiceImpl.class);

    @Autowired
    private EmissionsReportRepository erRepo;

    @Autowired
    private EmissionsReportMapper emissionsReportMapper;

    @Autowired
    private RepoLocator repoLocator;


    /**
     * Approve the specified reports and move to approved
     * @param reportIds
     * @return
     */
    @Override
    public List<EmissionsReportDto> acceptEmissionsReports(List<Long> reportIds) {
        return updateEmissionsReportsStatus(reportIds, ReportStatus.APPROVED);
    }

    /**
     * Reject the specified reports. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    @Override
    public List<EmissionsReportDto> rejectEmissionsReports(List<Long> reportIds) {
        return updateEmissionsReportsStatus(reportIds, ReportStatus.IN_PROGRESS, ValidationStatus.UNVALIDATED);
    }

    /**
     * Reset report status. Sets report status to in progress and validation status to unvalidated.
     * @param reportIds
     * @return
     */
    @Override
    public List<EmissionsReportDto> resetEmissionsReport(List<Long> reportIds) {
        return updateEmissionsReportsStatus(reportIds, ReportStatus.IN_PROGRESS, ValidationStatus.UNVALIDATED);
    }

    /**
     * Find the report for an entity using the provided repository class and reset the report status.
     * Sets report status to in progress and validation status to unvalidated.
     * @param entityIds
     * @param repoClazz
     * @return
     */
    @Override
    public <T extends ReportIdRetriever> List<EmissionsReportDto> resetEmissionsReportForEntity(List<Long> entityIds, Class<T> repoClazz) {

        ReportIdRetriever repo = repoLocator.getReportIdRepository(repoClazz);

        List<Long> reportIds = entityIds.stream()
        .map(id -> {

            return repo.retrieveEmissionsReportById(id)
                .orElseThrow(() -> {

                    String entity = repoClazz.getSimpleName().replace("Repository", "");
                    return new NotExistException(entity, id);
                });
        }).collect(Collectors.toList());

        return updateEmissionsReportsStatus(reportIds, ReportStatus.IN_PROGRESS, ValidationStatus.UNVALIDATED);
    }

    /**
     * Update the status of the specified reports
     * @param reportIds
     * @param status
     * @param validationStatus
     * @return
     */
    private List<EmissionsReportDto> updateEmissionsReportsStatus(List<Long> reportIds, ReportStatus status, ValidationStatus validationStatus) {

        return StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
            .map(report -> {
                if ((status != null && !status.equals(report.getStatus()))
                        || (validationStatus != null && !validationStatus.equals(report.getValidationStatus()))) {
                    if (status != null) {
                        report.setStatus(status);
                    }
                    if(validationStatus != null) {
                        report.setValidationStatus(validationStatus);
                    }
                    return this.emissionsReportMapper.toDto(this.erRepo.save(report));
                }
                return this.emissionsReportMapper.toDto(report);
            }).collect(Collectors.toList());

    }
    
    /**
     * Update the status of the specified reports
     * @param reportIds
     * @param status
     * @return
     */
    private List<EmissionsReportDto> updateEmissionsReportsStatus(List<Long> reportIds, ReportStatus status) {
        return updateEmissionsReportsStatus(reportIds, status, null);
    }
}
