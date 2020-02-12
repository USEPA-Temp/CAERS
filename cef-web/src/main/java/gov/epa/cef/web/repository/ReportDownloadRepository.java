package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.epa.cef.web.domain.ReportDownloadView;

public interface ReportDownloadRepository extends JpaRepository<ReportDownloadView, Long> {

    List<ReportDownloadView> findByReportId(Long reportId);

}