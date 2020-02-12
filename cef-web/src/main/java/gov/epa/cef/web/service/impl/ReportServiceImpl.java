package gov.epa.cef.web.service.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.service.UserService;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.ReportDownloadRepository;
import gov.epa.cef.web.repository.ReportHistoryRepository;
import gov.epa.cef.web.repository.ReportSummaryRepository;
import gov.epa.cef.web.service.mapper.ReportDownloadMapper;
import gov.epa.cef.web.service.mapper.ReportHistoryMapper;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;
import gov.epa.cef.web.service.dto.ReportDownloadDto;
import gov.epa.cef.web.service.dto.ReportHistoryDto;
import gov.epa.cef.web.service.dto.ReportSummaryDto;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.ReportAction;
import gov.epa.cef.web.domain.ReportDownloadView;
import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.domain.ReportSummary;

@Service
public class ReportServiceImpl implements ReportService {
    
    Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);
    
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
     * @param reportId
     * @param reportAction
     * @param comments
     */
    public void createReportHistory(List<Long> reportIds, ReportAction reportAction, String comments) {
	    	StreamSupport.stream(this.erRepo.findAllById(reportIds).spliterator(), false)
	      .forEach(report -> {
		    		ReportHistory rptActionLog = new ReportHistory();
		    		rptActionLog.setUserId(userService.getCurrentUser().getCdxUserId());
		    		rptActionLog.setUserFullName(userService.getCurrentUser().getFirstName() + " " + userService.getCurrentUser().getLastName());
			    	rptActionLog.setEmissionsReport(report);
			    	rptActionLog.setReportAction(reportAction);
			    	rptActionLog.setComments(comments);
			    	
			    	reportHistoryRepo.save(rptActionLog); 	
	      });
	  }
    
	    /**
	     * Create Report History records for specified reports
	     * @param reportId
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
		    	EmissionsReport emissionsReportId = erRepo.findById(reportId)
		    			.orElseThrow(() -> new IllegalStateException(String.format("Emissions report %s does not exist.", reportId)));
		    	
	    		ReportHistory rptActionLog = new ReportHistory();
	    		rptActionLog.setUserId(userService.getCurrentUser().getCdxUserId());
	    		rptActionLog.setUserFullName(userService.getCurrentUser().getFirstName() + " " + userService.getCurrentUser().getLastName());
		    	rptActionLog.setEmissionsReport(emissionsReportId);
		    	rptActionLog.setReportAction(reportAction);
		    	
		    	reportHistoryRepo.save(rptActionLog);
	    }
	    
	    /***
	     * Return ReportDownloadDto for the chosen report id
	     * @param report id
	     * @return
	     */
	    public List<ReportDownloadDto> retrieveReportDownloadDtoByReportId(Long reportId){
	        List<ReportDownloadView> reportDownloadsList = downloadRepo.findByReportId(reportId);
	        return downloadMapper.toDtoList(reportDownloadsList);
	    }	    
}
