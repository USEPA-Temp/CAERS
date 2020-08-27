package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository; 
import org.springframework.data.repository.query.Param;
import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.service.dto.UserFeedbackStatsDto.FeedbackStats;

import java.util.List;


public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Long> {

    /**
     * Retrieve User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    UserFeedback findByReportId(@Param("id") Long id);
    
    /**
     * Retrieve a List of User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    List<UserFeedback> findAllByReportId(@Param("id") Long id);
	
    /**
     * Retrieve a List of User Feedback for a year and agency code
     * @param year
     * @param agencyCode
     * @return userFeedback
     */
    List<UserFeedback> findByYearAndAgencyCode(Short year, String agencyCode, Sort sort);
    
    @Query("select distinct agencyCode FROM UserFeedback")
    List<String> findDistinctAgencies(Sort sort);
    
    @Query("select distinct year FROM UserFeedback")
    List<Short> findDistinctYears(Sort sort);
    
    @Query("select AVG(intuitiveRating) as intuitiveRateAvg, AVG(dataEntryScreens) as dataEntryScreensAvg, "
    		+ "AVG(dataEntryBulkUpload) as dataEntryBulkUploadAvg, AVG(calculationScreens) as calculationScreensAvg, "
    		+ "AVG(controlsAndControlPathAssignments) as controlsAndControlPathAssignAvg, "
    		+ "AVG(qualityAssuranceChecks) as qualityAssuranceChecksAvg, AVG(overallReportingTime) as overallReportingTimeAvg "
    		+ "FROM UserFeedback WHERE year = :year AND agencyCode = :agencyCode")
    FeedbackStats findAvgByYearAndAgency(@Param("year") Short year, @Param("agencyCode") String agency);
    
}
