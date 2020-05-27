package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionBulkEntryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private PollutantDto pollutant;
    private Boolean totalManualEntry;
    private BigDecimal overallControlPercent;
    private BigDecimal totalEmissions;
    private UnitMeasureCodeDto emissionsUomCode;
    private BigDecimal emissionsFactor;
    private CalculationMethodCodeDto emissionsCalcMethodCode;
    private UnitMeasureCodeDto emissionsNumeratorUom;
    private UnitMeasureCodeDto emissionsDenominatorUom;
    private BigDecimal previousTotalEmissions;
    private String previousEmissionsUomCode;

    private Boolean calculationFailed;
    private String calculationFailureMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PollutantDto getPollutant() {
        return pollutant;
    }

    public void setPollutant(PollutantDto pollutant) {
        this.pollutant = pollutant;
    }

    public Boolean getTotalManualEntry() {
        return totalManualEntry;
    }

    public void setTotalManualEntry(Boolean totalManualEntry) {
        this.totalManualEntry = totalManualEntry;
    }

    public BigDecimal getOverallControlPercent() {
        return overallControlPercent;
    }

    public void setOverallControlPercent(BigDecimal overallControlPercent) {
        this.overallControlPercent = overallControlPercent;
    }

    public BigDecimal getTotalEmissions() {
        return totalEmissions;
    }

    public void setTotalEmissions(BigDecimal totalEmissions) {
        this.totalEmissions = totalEmissions;
    }

    public UnitMeasureCodeDto getEmissionsUomCode() {
        return emissionsUomCode;
    }

    public void setEmissionsUomCode(UnitMeasureCodeDto emissionsUomCode) {
        this.emissionsUomCode = emissionsUomCode;
    }

    public BigDecimal getEmissionsFactor() {
        return emissionsFactor;
    }

    public void setEmissionsFactor(BigDecimal emissionsFactor) {
        this.emissionsFactor = emissionsFactor;
    }

    public CalculationMethodCodeDto getEmissionsCalcMethodCode() {
        return emissionsCalcMethodCode;
    }

    public void setEmissionsCalcMethodCode(CalculationMethodCodeDto emissionsCalcMethodCode) {
        this.emissionsCalcMethodCode = emissionsCalcMethodCode;
    }

    public UnitMeasureCodeDto getEmissionsNumeratorUom() {
        return emissionsNumeratorUom;
    }

    public void setEmissionsNumeratorUom(UnitMeasureCodeDto emissionsNumeratorUom) {
        this.emissionsNumeratorUom = emissionsNumeratorUom;
    }

    public UnitMeasureCodeDto getEmissionsDenominatorUom() {
        return emissionsDenominatorUom;
    }

    public void setEmissionsDenominatorUom(UnitMeasureCodeDto emissionsDenominatorUom) {
        this.emissionsDenominatorUom = emissionsDenominatorUom;
    }

    public BigDecimal getPreviousTotalEmissions() {
        return previousTotalEmissions;
    }

    public void setPreviousTotalEmissions(BigDecimal previousTotalEmissions) {
        this.previousTotalEmissions = previousTotalEmissions;
    }

    public String getPreviousEmissionsUomCode() {
        return previousEmissionsUomCode;
    }

    public void setPreviousEmissionsUomCode(String previousEmissionsUomCode) {
        this.previousEmissionsUomCode = previousEmissionsUomCode;
    }

    public Boolean isCalculationFailed() {
        return calculationFailed;
    }

    public void setCalculationFailed(Boolean calculationFailed) {
        this.calculationFailed = calculationFailed;
    }

    public String getCalculationFailureMessage() {
        return calculationFailureMessage;
    }

    public void setCalculationFailureMessage(String calculationFailureMessage) {
        this.calculationFailureMessage = calculationFailureMessage;
    }

}
