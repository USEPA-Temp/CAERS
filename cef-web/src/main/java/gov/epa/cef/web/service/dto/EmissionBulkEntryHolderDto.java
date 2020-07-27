package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmissionBulkEntryHolderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    // unit
    private Long emissionsUnitId;
    private String unitIdentifier;
    private String unitDescription;

    // process
    private Long emissionsProcessId;
    private String emissionsProcessIdentifier;
    private String emissionsProcessDescription;
    private CodeLookupDto operatingStatusCode;

    // reporting period
    private Long reportingPeriodId;
    private CodeLookupDto reportingPeriodTypeCode;
    
    private List<EmissionBulkEntryDto> emissions = new ArrayList<>();

    public Long getEmissionsUnitId() {
        return emissionsUnitId;
    }

    public void setEmissionsUnitId(Long emissionsUnitId) {
        this.emissionsUnitId = emissionsUnitId;
    }

    public String getUnitIdentifier() {
        return unitIdentifier;
    }

    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public Long getEmissionsProcessId() {
        return emissionsProcessId;
    }

    public void setEmissionsProcessId(Long emissionsProcessid) {
        this.emissionsProcessId = emissionsProcessid;
    }

    public String getEmissionsProcessIdentifier() {
        return emissionsProcessIdentifier;
    }

    public void setEmissionsProcessIdentifier(String emissionsProcessIdentifier) {
        this.emissionsProcessIdentifier = emissionsProcessIdentifier;
    }

    public String getEmissionsProcessDescription() {
        return emissionsProcessDescription;
    }

    public void setEmissionsProcessDescription(String processDescription) {
        this.emissionsProcessDescription = processDescription;
    }

    public CodeLookupDto getOperatingStatusCode() {
        return operatingStatusCode;
    }

    public void setOperatingStatusCode(CodeLookupDto operatingStatusCode) {
        this.operatingStatusCode = operatingStatusCode;
    }

    public Long getReportingPeriodId() {
        return reportingPeriodId;
    }

    public void setReportingPeriodId(Long reportingPeriodId) {
        this.reportingPeriodId = reportingPeriodId;
    }

    public CodeLookupDto getReportingPeriodTypeCode() {
        return reportingPeriodTypeCode;
    }

    public void setReportingPeriodTypeCode(CodeLookupDto reportingPeriodTypeCode) {
        this.reportingPeriodTypeCode = reportingPeriodTypeCode;
    }

    public List<EmissionBulkEntryDto> getEmissions() {
        return emissions;
    }

    public void setEmissions(List<EmissionBulkEntryDto> emissions) {
        this.emissions.clear();
        if (emissions != null) {
            this.emissions.addAll(emissions);
        }
    }

}
