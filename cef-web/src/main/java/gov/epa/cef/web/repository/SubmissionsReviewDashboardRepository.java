package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;

public interface SubmissionsReviewDashboardRepository extends JpaRepository<SubmissionsReviewDashboardView, Long> {

    List<SubmissionsReviewDashboardView> findByReportStatus(ReportStatus reportStatus);
    
    List<SubmissionsReviewDashboardView> findByYearAndReportStatus(Short year, ReportStatus reportStatus);
    
    List<SubmissionsReviewDashboardView> findByYear(Short year); 
    
    List<SubmissionsReviewDashboardView> findByAgencyCode(String agencyCode);

}
