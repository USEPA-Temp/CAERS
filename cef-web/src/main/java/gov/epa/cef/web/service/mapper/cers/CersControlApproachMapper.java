package gov.epa.cef.web.service.mapper.cers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPollutant;
import net.exchangenetwork.schema.cer._1._2.ControlApproachDataType;
import net.exchangenetwork.schema.cer._1._2.ControlPollutantDataType;

@Mapper(componentModel = "spring", uses = {})
public interface CersControlApproachMapper {

    @Mapping(source="control.description", target="controlApproachDescription")
    @Mapping(source="control.percentCapture", target="percentControlApproachCaptureEfficiency")
    @Mapping(source="control.percentControl", target="percentControlApproachEffectiveness")
//    @Mapping(source="", target="percentControlApproachPenetration")
//    @Mapping(source="", target="firstInventoryYear")
//    @Mapping(source="", target="lastInventoryYear")
    @Mapping(source="control.comments", target="controlApproachComment")
//    @Mapping(source="", target="controlMeasure")
    @Mapping(source="control.pollutants", target="controlPollutant")
    ControlApproachDataType fromControlAssignment(ControlAssignment source);

    @Mapping(source="pollutantCode", target="pollutantCode")
    @Mapping(source="percentReduction", target="percentControlMeasuresReductionEfficiency")
    ControlPollutantDataType pollutantFromControlPollutant(ControlPollutant source);
}
