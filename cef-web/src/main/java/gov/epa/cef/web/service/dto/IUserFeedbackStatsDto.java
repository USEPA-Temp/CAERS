package gov.epa.cef.web.service.dto;

/**
 * Created interface to use Spring Data projections to retrieve results from 
 * SQL queries with aggregate functions.
 * https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions
 */

public interface IUserFeedbackStatsDto {

		Long getIntuitiveRateAvg();
	    Long getDataEntryScreensAvg();
	    Long getDataEntryBulkUploadAvg();
	    Long getCalculationScreensAvg();
	    Long getControlsAndControlPathAssignAvg();
	    Long getQualityAssuranceChecksAvg();
	    Long getOverallReportingTimeAvg();
	    
}
