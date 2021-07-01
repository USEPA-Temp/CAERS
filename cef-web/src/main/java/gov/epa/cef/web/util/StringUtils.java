package gov.epa.cef.web.util;

public class StringUtils {

    /**
     * Return a string that can be used as a valid file name
     */
    public static String createValidFileName(String agencyFacilityIdentifier, String facilityName, Short reportYear) {
        String fileName = String.format("%s-%s-%d.xlsx", agencyFacilityIdentifier, facilityName, reportYear).replaceAll("[^a-zA-Z0-9-.]", " ");
        return fileName;
    }

}
