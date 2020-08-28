package gov.epa.cef.web.service.dto;

public class UserFeedbackStatsDto {

	public interface FeedbackStats {
		
		Long getIntuitiveRateAvg();
	    Long getDataEntryScreensAvg();
	    Long getDataEntryBulkUploadAvg();
	    Long getCalculationScreensAvg();
	    Long getControlsAndControlPathAssignAvg();
	    Long getQualityAssuranceChecksAvg();
	    Long getOverallReportingTimeAvg();
	}

}
