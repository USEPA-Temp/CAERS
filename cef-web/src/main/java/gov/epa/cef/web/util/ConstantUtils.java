package gov.epa.cef.web.util;

public class ConstantUtils {
	
	//OPERATING STATUSES
	public static final String STATUS_OPERATING = "OP";
	public static final String STATUS_PERMANENTLY_SHUTDOWN = "PS";
	public static final String STATUS_TEMPORARILY_SHUTDOWN = "TS";
	
	// RELEASE POINT TYPE CODES
	public static final String FUGITIVE_RELEASE_POINT_TYPE = "1";
	
	//EIS TRANSMISSION TYPES
	public static final String EIS_TRANSMISSION_FACILITY_INVENTORY = "FacilityInventory";
	public static final String EIS_TRANSMISSION_POINT_EMISSIONS = "Point";
	
	//LANDFILL FACILITY SOURCE TYPE CODE
	public static final String FACILITY_SOURCE_LANDFILL_CODE = "104";
	
	//NUMBER PATTERNS
	public static final String REGEX_ONE_DECIMAL_PRECISION = "^\\d{0,3}(\\.\\d{1})?$";
	
}
