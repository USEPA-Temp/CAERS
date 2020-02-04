package gov.epa.cef.web.service.validation;

public enum ValidationField {
    REPORT_YEAR("report.year"),
    REPORT_AGENCY_CODE("report.agencyCode"),
    REPORT_FRS_ID("report.frsFacilityId"),
    REPORT_EIS_ID("report.eisProgramId"),
    FACILITY_EIS_ID("report.facilitySite.eisProgramId"),
    FACILITY_CONTACT("report.facilitySite.contacts"),
    FACILITY_STATUS("report.facilitySite.status"),
    FACILITY_NAICS("report.facilitysite.naics"),
    RP_GAS_TEMP("report.facilitySite.releasePoint.exitGasTemperature"),
    RP_GAS_FLOW("report.facilitySite.releasePoint.exitGasFlowRate"),
    RP_GAS_VELOCITY("report.facilitySite.releasePoint.exitGasVelocity"),
    RP_GAS_RELEASE("report.facilitySite.releasePoint.release"),
    RP_FENCELINE("report.facilitySite.releasePoint.fenceLine"),
    RP_UOM_FT("report.facilitySite.releasePoint.uom"),
    RP_FUGITIVE("report.facilitySite.releasePoint.fugitive"),
    RP_STACK("report.facilitySite.releasePoint.stack"),
    RP_COORDINATE("report.facilitySite.releasePoint.coordinate"),
    PROCESS_RP_PCT("report.facilitySite.emissionsUnit.emissionsProcess.releasePointAppts.percent"),
    PROCESS_INFO_SCC("report.facilitySite.emissionsUnit.emissionsProcess.information.scc"),
    PERIOD_CALC_VALUE("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterValue"),
    PERIOD_CALC_MAT_CODE("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationMaterialCode"),
    PERIOD_CALC_TYPE_CODE("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterTypeCode"),
    PERIOD_CALC_UOM("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.calculationParameterUom"),
    PERIOD_EMISSION("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission"),
    DETAIL_AVG_HR_PER_DAY("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgHoursPerDay"),
    DETAIL_AVG_DAY_PER_WEEK("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgDaysPerWeek"),
    DETAIL_ACT_HR_PER_PERIOD("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.actualHoursPerPeriod"),
    DETAIL_AVG_WEEK_PER_PERIOD("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.avgWeeksPerPeriod"),
    DETAIL_PCT_SPRING("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentSpring"),
    DETAIL_PCT_SUMMER("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentSummer"),
    DETAIL_PCT_FALL("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentFall"),
    DETAIL_PCT_WINTER("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percentWinter"),
    DETAIL_PCT("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.operatingDetail.percents"),
    EMISSION_CALC_METHOD("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsCalcMethodCode"),
    EMISSION_COMMENTS("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.comments"),
    EMISSION_EF("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsFactor"),
    EMISSION_TOTAL_EMISSIONS("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.totalEmissions"),
    EMISSION_NUM_UOM("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsNumeratorUom"),
    EMISSION_DENOM_UOM("report.facilitySite.emissionsUnit.emissionsProcess.reportingPeriod.emission.emissionsDenominatorUom"),
    EMISSIONS_UNIT_STATUS_CODE("report.facilitySite.emissionsUnit.statusTypeCode"),
    EMISSIONS_UNIT_STATUS_YEAR("report.facilitySite.emissionsUnit.statusYear"),
    EMISSIONS_UNIT_CAPACITY("report.emissionsUnit.capacity")
//    EMISSION_(""),
    ;

    private final String value;

    ValidationField(String value) {
        this.value = value;
    }

//    public String code() {
//        return this.name();
//    }

    public String value() {
        return this.value;
    }
}
