/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.mapper.cers._1._2;

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
