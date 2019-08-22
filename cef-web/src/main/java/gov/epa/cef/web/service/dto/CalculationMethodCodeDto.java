package gov.epa.cef.web.service.dto;

public class CalculationMethodCodeDto extends CodeLookupDto {

    private static final long serialVersionUID = 1L;

    private Boolean totalDirectEntry;

    public Boolean getTotalDirectEntry() {
        return totalDirectEntry;
    }

    public void setTotalDirectEntry(Boolean totalDirectEntry) {
        this.totalDirectEntry = totalDirectEntry;
    }

}
