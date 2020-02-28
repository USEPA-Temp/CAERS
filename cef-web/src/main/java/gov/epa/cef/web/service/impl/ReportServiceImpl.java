package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportDownloadView;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.domain.ReportSummary;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportDownloadRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.repository.ReportSummaryRepository;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.service.dto.ReportDownloadDto;
import gov.epa.cef.web.service.dto.ReportHistoryDto;
import gov.epa.cef.web.service.dto.ReportSummaryDto;
import gov.epa.cef.web.service.dto.UserDto;
import gov.epa.cef.web.service.mapper.ReportDownloadMapper;
import gov.epa.cef.web.service.mapper.ReportHistoryMapper;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReportSummaryRepository reportSummaryRepo;

    @Autowired
    private ReportHistoryRepository reportHistoryRepo;

    @Autowired
    private EmissionsReportRepository erRepo;

    @Autowired
    ReportSummaryMapper reportSummaryMapper;

    @Autowired
    ReportHistoryMapper reportHistoryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ReportDownloadRepository downloadRepo;

    @Autowired
    private ReportDownloadMapper downloadMapper;

    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param reportYear
     * @param facilitySiteId
     * @return
     */
    public List<ReportSummaryDto> findByReportYearAndFacilitySiteId(Short reportYear, Long facilitySiteId) {
        LOGGER.debug("findByReportYearAndFacilitySiteId - Entering");

        List<ReportSummary> reportSummary = reportSummaryRepo.findByReportYearAndFacilitySiteId(reportYear, facilitySiteId,Sort.by(Sort.DEFAULT_DIRECTION.ASC, "pollutantName"));
        return reportSummaryMapper.toDtoList(reportSummary);
    }

    /***
     * Return list of report history records for specified report
     * @param reportId
     * @return
     */
    public List<ReportHistoryDto> findByEmissionsReportId(Long reportId) {
        List<ReportHistory> reportHistory = reportHistoryRepo.findByEmissionsReportIdOrderByActionDate(reportId);
        return reportHistoryMapper.toDtoList(reportHistory);
    }

    /**
     * Create Report History records for specified reports
     * @param reportIds
     * @param reportAction
     * @param comments
     */
    public void createReportHistory(List<Long> reportIds, ReportAction reportAction, String comments) {
        UserDto appUser = this.userService.getCurrentUser();
        String userId = appUser.getCdxUserId();
        String fullName = String.format("%s %s", appUser.getFirstName(), appUser.getLastName());

        LOGGER.debug("Current User {}", appUser);

        Set<Long> auditedIds = new HashSet<>();

        this.erRepo.findAllById(reportIds)
            .forEach(report -> {

                auditedIds.add(report.getId());

                ReportHistory rptActionLog = new ReportHistory();
                rptActionLog.setUserId(userId);
                rptActionLog.setUserFullName(fullName);
                rptActionLog.setEmissionsReport(report);
                rptActionLog.setReportAction(reportAction);
                rptActionLog.setComments(comments);

                reportHistoryRepo.save(rptActionLog);
            });

        // ensure what was passed in matches what for-looped
        // set will return false if id is already in the list, i.e. none should match
        if (reportIds.stream().anyMatch(auditedIds::add)) {

            // remove the ones we did do
            reportIds.removeAll(auditedIds);

            String msg = String.format("Emissions report(s) %s does not exist.",
                reportIds.stream().map(Object::toString).collect(Collectors.joining(", ")));

            throw new IllegalStateException(msg);
        }
    }

    /**
     * Create Report History records for specified reports
     * @param reportIds
     * @param reportAction
     */
    public void createReportHistory(List<Long> reportIds, ReportAction reportAction) {
        createReportHistory(reportIds, reportAction, null);
    }

    /**
     * Create Report History record
     * @param reportId
     * @param reportAction
     */
    public void createReportHistory(Long reportId, ReportAction reportAction) {

        createReportHistory(Collections.singletonList(reportId), reportAction);
    }

    /***
     * Return ReportDownloadDto for the chosen report id
     * @param reportId
     * @return
     */
    public List<ReportDownloadDto> retrieveReportDownloadDtoByReportId(Long reportId){
        List<ReportDownloadView> reportDownloadsList = downloadRepo.findByReportId(reportId);
        return downloadMapper.toDtoList(reportDownloadsList);
    }
}
