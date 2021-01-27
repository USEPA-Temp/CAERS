package gov.epa.cef.web.service.mapper.cers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPollutant;
import net.exchangenetwork.schema.cer._1._2.ControlApproachDataType;
import net.exchangenetwork.schema.cer._1._2.ControlPollutantDataType;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {})
public interface CersControlApproachMapper {

    @Mapping(source="control.description", target="controlApproachDescription")
    @Mapping(source="control.percentControl", target="percentControlApproachEffectiveness")
    @Mapping(source="control.comments", target="controlApproachComment")
    @Mapping(source="control.pollutants", target="controlPollutant")
    ControlApproachDataType fromControlAssignment(ControlAssignment source);

    @Mapping(source="pollutant.pollutantCode", target="pollutantCode")
    @Mapping(source="percentReduction", target="percentControlMeasuresReductionEfficiency")
    ControlPollutantDataType pollutantFromControlPollutant(ControlPollutant source);
}
