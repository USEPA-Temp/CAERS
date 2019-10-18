package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;

public interface SubmissionsReviewDashboardRepository extends JpaRepository<SubmissionsReviewDashboardView, Long> {

    List<SubmissionsReviewDashboardView> findByAgencyCode(String agencyCode);
}
