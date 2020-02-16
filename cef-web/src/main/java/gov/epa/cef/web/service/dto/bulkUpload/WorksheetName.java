package gov.epa.cef.web.service.dto.bulkUpload;

public enum WorksheetName {
    FacilitySite("Facility"),
    EmissionsUnit("Emission Units"),
    ReleasePoint("Release Points"),
    EmissionsProcess("Emission Processes"),
    ReportingPeriod("Reporting Period"),
    Emission("Emissions"),
    ReleasePointAppt("Apportionment"),
    OperatingDetail("Operating Details"),
    ControlPath("Control Paths"),
    Control("Controls"),
    ControlAssignment("Control Assignments"),
    ControlPollutant("Control Pollutant"),
    FacilitySiteContact("Facility Contacts"),
    FacilityNaics("NAICS");

    private final String sheetName;

    WorksheetName(String sheetName) {

        this.sheetName = sheetName;
    }

    public String sheetName() {

        return this.sheetName;
    }
}