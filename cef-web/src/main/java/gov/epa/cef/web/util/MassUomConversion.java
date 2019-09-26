package gov.epa.cef.web.util;

import java.math.BigDecimal;

public enum MassUomConversion {
    G(new BigDecimal("1.10231e-6")),
    MILLIGRM(new BigDecimal("1.10231e-9")),
    NG(new BigDecimal("1.10231e-12")),
    UG(new BigDecimal("1.10231e-15")),
    KG(new BigDecimal("1.10231e-3")),
    LB(new BigDecimal(".0005")),
    MEGAGRAM(new BigDecimal("1.10231")),
    TON(new BigDecimal("1"));

    private final BigDecimal conversionFactor;

    MassUomConversion(BigDecimal conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public String code() {
        return this.name();
    }

    public BigDecimal conversionFactor() {
        return this.conversionFactor;
    }

}
