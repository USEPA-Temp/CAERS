package gov.epa.cef.web.service.dto;

public class UnitMeasureCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;

    private Boolean efNumerator;

    private Boolean efDenominator;

    public Boolean getEfNumerator() {
        return efNumerator;
    }

    public void setEfNumerator(Boolean efNumerator) {
        this.efNumerator = efNumerator;
    }

    public Boolean getEfDenominator() {
        return efDenominator;
    }

    public void setEfDenominator(Boolean efDenominator) {
        this.efDenominator = efDenominator;
    }


}
