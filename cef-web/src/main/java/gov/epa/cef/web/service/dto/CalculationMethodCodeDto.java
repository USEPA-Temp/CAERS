package gov.epa.cef.web.service.dto;

public class CalculationMethodCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;

    private Boolean controlIndicator;
    private Boolean epaEmissionFactor;
    private Boolean totalDirectEntry;

    public Boolean getControlIndicator() {
        return controlIndicator;
    }

    public void setControlIndicator(Boolean controlIndicator) {
        this.controlIndicator = controlIndicator;
    }

    public Boolean getEpaEmissionFactor() {
        return epaEmissionFactor;
    }

    public void setEpaEmissionFactor(Boolean epaEmissionFactor) {
        this.epaEmissionFactor = epaEmissionFactor;
    }

    public Boolean getTotalDirectEntry() {
        return totalDirectEntry;
    }

    public void setTotalDirectEntry(Boolean totalDirectEntry) {
        this.totalDirectEntry = totalDirectEntry;
    }

}
