package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class UserFeedbackStatsDto implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Short> availableYears;
    private List<String> availableAgencies;
    
	public List<Short> getAvailableYears() {
		return availableYears;
	}

	public void setAvailableYears(List<Short> availableYears) {
		this.availableYears = availableYears;
	}

	public List<String> getAvailableAgencies() {
		return availableAgencies;
	}

	public void setAvailableAgencies(List<String> availableAgencies) {
		this.availableAgencies = availableAgencies;
	}

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
