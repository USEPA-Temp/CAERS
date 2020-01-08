package gov.epa.cef.web.client.api;

import gov.epa.cef.web.domain.AircraftEngineTypeCode;
import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointTypeCode;
import gov.epa.cef.web.domain.UnitTypeCode;
import gov.epa.client.frs.iptquery.model.Process;
import gov.epa.client.frs.iptquery.model.Unit;
import org.apache.commons.lang.StringUtils;

import java.util.function.Function;

import static gov.epa.cef.web.client.api.FrsUtil.bigToDbl;
import static gov.epa.cef.web.client.api.FrsUtil.createOperatingStatus;
import static gov.epa.cef.web.client.api.FrsUtil.createUnitMeasure;
import static gov.epa.cef.web.client.api.FrsUtil.intToShort;

public class FrsSubfacilityTransforms {

    public static Function<gov.epa.client.frs.iptquery.model.Control, Control> toControl(FacilitySite facilitySite) {

        return control -> {

            Control result = new Control();

            result.setFacilitySite(facilitySite);

            result.setIdentifier(control.getControlId());
            result.setPercentControl(bigToDbl(control.getControlEffectivenessPercent()));
            result.setPercentCapture(bigToDbl(control.getControlCaptureEfficiencyPercent()));

            result.setOperatingStatusCode(createOperatingStatus(control.getControlOperatingStatus()));

            return result;
        };
    }

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
            result.setOperatingStatusCode(createOperatingStatus(unit.getUnitOperatingStatus()));

            result.setStatusYear(intToShort(unit.getUnitOperatingStatusYear()));

            String typecode = unit.getUnitTypeCode();
            if (typecode != null) {

                UnitTypeCode unitTypeCode = new UnitTypeCode();
                unitTypeCode.setCode(typecode);
                result.setUnitTypeCode(unitTypeCode);
            }

//            result.setTypeCodeDescription(unit.getUnitTypeDescription());

            result.setUnitOfMeasureCode(createUnitMeasure(unit.getUnitDesignCapacityUomCode()));

            return result;
        };
    }

    public static Function<Process, EmissionsProcess> toProcess(EmissionsUnit emissionsUnit) {

        return process -> {

            EmissionsProcess result = new EmissionsProcess();

            result.setEmissionsProcessIdentifier(process.getProcessId());
            result.setEmissionsUnit(emissionsUnit);

            result.setDescription(process.getProcessDescription());
            result.setSccCode(process.getProcessSccCode());

            result.setStatusYear(intToShort(process.getProcessOperatingStatusYear()));

            result.setOperatingStatusCode(createOperatingStatus(process.getProcessOperatingStatus()));

            String aircode = process.getProcessAircraftEngineTypeCode();
            if (aircode != null) {

                AircraftEngineTypeCode aircraftEngineTypeCode = new AircraftEngineTypeCode();
                aircraftEngineTypeCode.setCode(aircode);

                process.setProcessAircraftEngineTypeCode(aircode);
            }

            return result;
        };
    }

    public static Function<gov.epa.client.frs.iptquery.model.ReleasePoint, ReleasePoint> toReleasePoint(FacilitySite facilitySite) {

        return release -> {

            ReleasePoint result = new ReleasePoint();

            result.setFacilitySite(facilitySite);
            result.setReleasePointIdentifier(release.getReleasePointId());

            // TODO description is non-nullable
            result.setDescription(release.getReleasePointTypeName());

            result.setExitGasFlowRate(bigToDbl(release.getReleasePointExitGasFlowRateMeasure()));

            // TODO this is losing precision
            result.setExitGasTemperature(release.getReleasePointExitTemperatureMeasure() == null
                ? null : release.getReleasePointExitTemperatureMeasure().shortValue());

            result.setExitGasVelocity(bigToDbl(release.getReleasePointExitGasVelocityMeasure()));

            result.setFugitiveLine1Latitude(bigToDbl(release.getReleasePointFugitiveLinePt1Latitude()));
            result.setFugitiveLine2Latitude(bigToDbl(release.getReleasePointFugitiveLinePt2Latitude()));
            result.setFugitiveLine1Longitude(bigToDbl(release.getReleasePointFugitiveLinePt1Longitude()));
            result.setFugitiveLine2Longitude(bigToDbl(release.getReleasePointFugitiveLinePt2Longitude()));

            result.setLatitude(bigToDbl(release.getReleasePointLatitude()));
            result.setLongitude(bigToDbl(release.getReleasePointLongitude()));

            String syscode = release.getReleasePointSourceSystemProgramCode();
            if (syscode != null) {

                ProgramSystemCode programSystemCode = new ProgramSystemCode();
                programSystemCode.setCode(syscode);
                result.setProgramSystemCode(programSystemCode);
            }

            result.setStackDiameter(bigToDbl(release.getReleasePointStackDiameterMeasure()));
            result.setStackHeight(bigToDbl(release.getReleasePointStackHeightMeasure()));
            result.setStackDiameterUomCode(createUnitMeasure(release.getReleasePointStackDiameterUomCode()));
            result.setStackHeightUomCode(createUnitMeasure(release.getReleasePointStackHeightUomCode()));

            result.setStatusYear(intToShort(release.getReleasePointOperatingStatusYear()));

            // TODO do we need translation from FRS to CAER?
            String typecode = StringUtils.defaultIfBlank(release.getReleasePointTypeCode(), "99")
                .replaceAll("^07$", "99")
                .replaceAll("^0", "");

            ReleasePointTypeCode releasePointTypeCode = new ReleasePointTypeCode();
            releasePointTypeCode.setCode(typecode);
            result.setTypeCode(releasePointTypeCode);

            result.setOperatingStatusCode(createOperatingStatus(release.getReleasePointOperatingStatus()));

            result.setExitGasVelocityUomCode(createUnitMeasure(release.getReleasePointExitGasVelocityUomCode()));
            result.setExitGasFlowUomCode(createUnitMeasure(release.getReleasePointExitGasFlowRateUomCode()));

            return result;
        };
    }
}
