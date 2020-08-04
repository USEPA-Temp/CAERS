package gov.epa.cef.web.service.mapper.cers;

import gov.epa.cef.web.domain.ReleasePoint;
import net.exchangenetwork.schema.cer._1._2.GeographicCoordinatesDataType;
import net.exchangenetwork.schema.cer._1._2.IdentificationDataType;
import net.exchangenetwork.schema.cer._1._2.ReleasePointDataType;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {})
public interface CersReleasePointMapper {

	@AfterMapping
    default void afterMapping(@MappingTarget ReleasePointDataType target, ReleasePoint source) {
        if (source.getLatitude() == null && source.getLongitude() == null) {
            target.setReleasePointGeographicCoordinates(null);
        }
    }
	
    @Mapping(source="typeCode.code", target="releasePointTypeCode")
    @Mapping(source="description", target="releasePointDescription")
    @Mapping(source="stackHeight", target="releasePointStackHeightMeasure")
    @Mapping(source="stackHeightUomCode.code", target="releasePointStackHeightUnitofMeasureCode")
    @Mapping(source="stackDiameter", target="releasePointStackDiameterMeasure")
    @Mapping(source="stackDiameterUomCode.code", target="releasePointStackDiameterUnitofMeasureCode")
    @Mapping(source="exitGasVelocity", target="releasePointExitGasVelocityMeasure")
    @Mapping(source="exitGasVelocityUomCode.code", target="releasePointExitGasVelocityUnitofMeasureCode")
    @Mapping(source="exitGasFlowRate", target="releasePointExitGasFlowRateMeasure")
    @Mapping(source="exitGasFlowUomCode.code", target="releasePointExitGasFlowRateUnitofMeasureCode")
    @Mapping(source="exitGasTemperature", target="releasePointExitGasTemperatureMeasure")
    @Mapping(source="fenceLineDistance", target="releasePointFenceLineDistanceMeasure")
    @Mapping(source="fenceLineUomCode.code", target="releasePointFenceLineDistanceUnitofMeasureCode")
    @Mapping(source="fugitiveHeight", target="releasePointFugitiveHeightMeasure")
    @Mapping(source="fugitiveHeightUomCode.code", target="releasePointFugitiveHeightUnitofMeasureCode")
    @Mapping(source="fugitiveWidth", target="releasePointFugitiveWidthMeasure")
    @Mapping(source="fugitiveWidthUomCode.code", target="releasePointFugitiveWidthUnitofMeasureCode")
    @Mapping(source="fugitiveLength", target="releasePointFugitiveLengthMeasure")
    @Mapping(source="fugitiveLengthUomCode.code", target="releasePointFugitiveLengthUnitofMeasureCode")
    @Mapping(source="fugitiveAngle", target="releasePointFugitiveAngleMeasure")
    @Mapping(source="comments", target="releasePointComment")
    @Mapping(source="operatingStatusCode.code", target="releasePointStatusCode")
    @Mapping(source="statusYear", target="releasePointStatusCodeYear")
    @Mapping(source=".", target="releasePointIdentification")
    @Mapping(source=".", target="releasePointGeographicCoordinates")
    ReleasePointDataType fromReleasePoint(ReleasePoint source);

    @Mapping(source="latitude", target="latitudeMeasure")
    @Mapping(source="longitude", target="longitudeMeasure")
    GeographicCoordinatesDataType coordinatesFromReleasePoint(ReleasePoint source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="releasePointIdentifier", target="identifier")
    @Mapping(source="facilitySite.programSystemCode.code", target="programSystemCode")
    IdentificationDataType identificationFromReleasePoint(ReleasePoint source);

    default List<IdentificationDataType> identificationListFromReleasePoint(ReleasePoint source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(identificationFromReleasePoint(source));
    }
}
