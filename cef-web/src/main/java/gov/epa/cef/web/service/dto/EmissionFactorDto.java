package gov.epa.cef.web.service.dto;

import java.math.BigDecimal;
import java.util.List;

public class EmissionFactorDto {

    private Long id;
    private UnitMeasureCodeDto emissionsNumeratorUom;
    private UnitMeasureCodeDto emissionsDenominatorUom;
    private CodeLookupDto calculationParameterTypeCode;
    private CodeLookupDto calculationMaterialCode;
    private CodeLookupDto controlMeasureCode;
    private Integer sccCode;
    private String pollutantCode;
    private Boolean formulaIndicator;
    private Boolean controlIndicator;
    private BigDecimal emissionFactor;
    private String emissionFactorFormula;
    private String description;
    private String note;
    private String source;
    private List<EmissionFormulaVariableCodeDto> variables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CodeLookupDto getCalculationParameterTypeCode() {
        return calculationParameterTypeCode;
    }

    public void setCalculationParameterTypeCode(CodeLookupDto calculationParameterTypeCode) {
        this.calculationParameterTypeCode = calculationParameterTypeCode;
    }

    public CodeLookupDto getCalculationMaterialCode() {
        return calculationMaterialCode;
    }

    public void setCalculationMaterialCode(CodeLookupDto calculationMaterialCode) {
        this.calculationMaterialCode = calculationMaterialCode;
    }
    
    public CodeLookupDto getControlMeasureCode() {
        return controlMeasureCode;
    }

    public void setControlMeasureCode(CodeLookupDto controlMeasureCode) {
        this.controlMeasureCode = controlMeasureCode;
    }

    public Integer getSccCode() {
        return sccCode;
    }

    public void setSccCode(Integer sccCode) {
        this.sccCode = sccCode;
    }

    public String getPollutantCode() {
        return pollutantCode;
    }

    public void setPollutantCode(String pollutantCode) {
        this.pollutantCode = pollutantCode;
    }

    public Boolean getFormulaIndicator() {
        return formulaIndicator;
    }

    public void setFormulaIndicator(Boolean formulaIndicator) {
        this.formulaIndicator = formulaIndicator;
    }

    public Boolean getControlIndicator() {
        return controlIndicator;
    }

    public void setControlIndicator(Boolean controlIndicator) {
        this.controlIndicator = controlIndicator;
    }

    public BigDecimal getEmissionFactor() {
        return emissionFactor;
    }

    public void setEmissionFactor(BigDecimal emissionFactor) {
        this.emissionFactor = emissionFactor;
    }

    public String getEmissionFactorFormula() {
        return emissionFactorFormula;
    }

    public void setEmissionFactorFormula(String emissionFactorFormula) {
        this.emissionFactorFormula = emissionFactorFormula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<EmissionFormulaVariableCodeDto> getVariables() {
        return variables;
    }

    public void setVariables(List<EmissionFormulaVariableCodeDto> variables) {
        this.variables = variables;
    }
    
}
