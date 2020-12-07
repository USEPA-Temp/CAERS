package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;

public interface SubmissionsReviewDashboardRepository extends JpaRepository<SubmissionsReviewDashboardView, Long> {

    List<SubmissionsReviewDashboardView> findByReportStatusAndProgramSystemCode(ReportStatus reportStatus, String programSystemCode);
    
    List<SubmissionsReviewDashboardView> findByYearAndReportStatusAndProgramSystemCode(Short year, ReportStatus reportStatus, String programSystemCode);
    
    List<SubmissionsReviewDashboardView> findByYearAndProgramSystemCode(Short year, String programSystemCode); 
    
    List<SubmissionsReviewDashboardView> findByProgramSystemCode(String programSystemCode);

}
