package gov.epa.cef.web.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.epa.cef.web.service.ReportService;
import gov.epa.cef.web.repository.ReportSummaryRepository;
import gov.epa.cef.web.service.mapper.ReportSummaryMapper;
import gov.epa.cef.web.service.dto.ReportSummaryDto;
import gov.epa.cef.web.domain.ReportSummary;

@Service
public class ReportServiceImpl implements ReportService {
    
    Logger LOGGER = LoggerFactory.getLogger(ReportServiceImpl.class);
    
    @Autowired
    private ReportSummaryRepository reportSummaryRepo;
    
    @Autowired
    ReportSummaryMapper reportSummaryMapper;
    
    /***
     * Return list of report summary records with total emissions summed per pollutant for the chosen facility and year
     * @param reportYear
     * @param facilitySiteId
     * @return
     */
    public List<ReportSummaryDto> findByReportYearAndFacilitySiteId(Short reportYear, Long facilitySiteId) {
        LOGGER.debug("findByReportYearAndFacilitySiteId - Entering");

        List<ReportSummary> reportSummary = reportSummaryRepo.findByReportYearAndFacilitySiteId(reportYear, facilitySiteId);
        return reportSummaryMapper.toDtoList(reportSummary);
    }

}
