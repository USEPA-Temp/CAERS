package gov.epa.cef.web.client.api;

import gov.epa.cef.web.domain.FipsCounty;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.UnitMeasureCode;

import java.math.BigDecimal;

class FrsUtil {

    static Double bigToDbl(BigDecimal big) {

        if (big == null) {
            return null;
        }

        return big.doubleValue();
    }

    static FipsCounty createCounty(String code) {

        FipsCounty result = new FipsCounty();

        if (code == null) {

            return null;

        }

        result.setCode(code);

        return result;
    }

    static OperatingStatusCode createOperatingStatus(String code) {

        OperatingStatusCode result = new OperatingStatusCode();

        if (code == null) {

            code = "OP";

        } else {

            // TODO do we need translation from FRS to CAER?
            switch (code) {
                case "OPERATING":
                    code = "OP";
                    break;
                case "PERMANENTLY SHUTDOWN":
                    code = "PS";
                    break;
                default:
                    // do nothing
            }
        }

        result.setCode(code);

        return result;
    }

    static UnitMeasureCode createUnitMeasure(String code) {

        UnitMeasureCode result = null;

        if (code != null) {
            result = new UnitMeasureCode();

            // TODO do we need translation from FRS to CAER?
            switch (code) {
                case "FEET":
                    code = "FT";
                    break;
                default:
                    // do nothing
            }

            result.setCode(code);
        }

        return result;
    }

    public static Short intToShort(Integer integer) {

        if (integer == null) {
            return null;
        }

        return integer.shortValue();
    }
}
