package gov.epa.cef.web.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository; 
import org.springframework.data.repository.query.Param;
import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.service.dto.IUserFeedbackStatsDto;

import java.util.List;


public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Long> {

    /**
     * Retrieve User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    UserFeedback findByEmissionsReportId(@Param("id") Long id);
    
    /**
     * Retrieve a List of User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    List<UserFeedback> findAllByEmissionsReportId(@Param("id") Long id);
	
    /**
     * Retrieve a List of User Feedback for a year and agency code
     * @param year
     * @param agencyCode
     * @return userFeedback
     */
    List<UserFeedback> findByYearAndAgencyCode(Short year, String agencyCode, Sort sort);
    
    /**
     * Retrieve a List of User Feedback for a year and all agencies
     * @param year
     * @return userFeedback
     */
    List<UserFeedback> findAllByYear(Short year, Sort sort);
    
    @Query("select distinct agencyCode FROM UserFeedback")
    List<String> findDistinctAgencies(Sort sort);
    
    @Query("select distinct year FROM UserFeedback")
    List<Short> findDistinctYears(Sort sort);
    
    @Query("select ROUND(AVG(intuitiveRating),0) as intuitiveRateAvg, ROUND(AVG(dataEntryScreens),0) as dataEntryScreensAvg, "
    		+ "ROUND(AVG(dataEntryBulkUpload),0) as dataEntryBulkUploadAvg, ROUND(AVG(calculationScreens),0) as calculationScreensAvg, "
    		+ "ROUND(AVG(controlsAndControlPathAssignments),0) as controlsAndControlPathAssignAvg, "
    		+ "ROUND(AVG(qualityAssuranceChecks),0) as qualityAssuranceChecksAvg, ROUND(AVG(overallReportingTime),0) as overallReportingTimeAvg "
    		+ "FROM UserFeedback WHERE year = :year AND agencyCode = :agencyCode")
    IUserFeedbackStatsDto findAvgByYearAndAgency(@Param("year") Short year, @Param("agencyCode") String agency);
    
    @Query("select ROUND(AVG(intuitiveRating),0) as intuitiveRateAvg, ROUND(AVG(dataEntryScreens),0) as dataEntryScreensAvg, "
    		+ "ROUND(AVG(dataEntryBulkUpload),0) as dataEntryBulkUploadAvg, ROUND(AVG(calculationScreens),0) as calculationScreensAvg, "
    		+ "ROUND(AVG(controlsAndControlPathAssignments),0) as controlsAndControlPathAssignAvg, "
    		+ "ROUND(AVG(qualityAssuranceChecks),0) as qualityAssuranceChecksAvg, ROUND(AVG(overallReportingTime),0) as overallReportingTimeAvg "
    		+ "FROM UserFeedback WHERE year = :year")
    IUserFeedbackStatsDto findAvgByYear(@Param("year") Short year);
    
}
