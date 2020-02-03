package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.ReportStatus;
import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;

public interface SubmissionsReviewDashboardRepository extends JpaRepository<SubmissionsReviewDashboardView, Long> {

    List<SubmissionsReviewDashboardView> findByReportStatusAndAgencyCode(ReportStatus reportStatus, String agencyCode);
    
    List<SubmissionsReviewDashboardView> findByYearAndReportStatusAndAgencyCode(Short year, ReportStatus reportStatus, String agencyCode);
    
    List<SubmissionsReviewDashboardView> findByYearAndAgencyCode(Short year, String agencyCode); 
    
    List<SubmissionsReviewDashboardView> findByAgencyCode(String agencyCode);

}
