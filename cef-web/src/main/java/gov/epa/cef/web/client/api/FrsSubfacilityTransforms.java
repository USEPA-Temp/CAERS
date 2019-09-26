package gov.epa.cef.web.client.api;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.client.frs.iptquery.model.Unit;

import java.util.function.Function;

public class FrsSubfacilityTransforms {

    public static Function<Unit, EmissionsUnit> toEmissionsUnit(FacilitySite facilitySite) {

        return unit -> {

            EmissionsUnit result = new EmissionsUnit();

            result.setUnitIdentifier(unit.getUnitId());
            result.setComments(unit.getUnitComment());
            result.setDescription(unit.getUnitDescription());
            result.setDesignCapacity(unit.getUnitDesignCapacity());
            result.setFacilitySite(facilitySite);

            // TODO verify this mapping is correct
            result.setProgramSystemCode(unit.getUnitSourceSystemProgramCode());

            // TODO do we want to default to OP - Operating
            String opstatus = unit.getUnitOperatingStatus();
            if (opstatus != null) {

                OperatingStatusCode operatingStatusCode = new OperatingStatusCode();
                operatingStatusCode.setCode(opstatus);
                result.setOperatingStatusCode(operatingStatusCode);
            }

            result.setStatusYear(
                unit.getUnitOperatingStatusYear() == null ? null : unit.getUnitOperatingStatusYear().shortValue());

            String typecode = unit.getUnitTypeCode();
            if (typecode != null) {

                UnitTypeCode utc = new UnitTypeCode();
                utc.setCode(typecode);

                result.setUnitTypeCode(utc);
            }

            result.setTypeCodeDescription(unit.getUnitTypeDescription());

            String uom = unit.getUnitDesignCapacityUomCode();
            if (uom != null) {
                UnitMeasureCode uomcode = new UnitMeasureCode();
                uomcode.setCode(uom);

                result.setUnitOfMeasureCode(uomcode);
            }

            return result;
        };
    }
}
