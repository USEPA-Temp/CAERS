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
package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.service.dto.EmissionsUnitDto;
import gov.epa.cef.web.service.dto.postOrder.EmissionsUnitPostOrderDto;

@Mapper(componentModel = "spring", uses = {EmissionsProcessMapper.class, LookupEntityMapper.class})   
public interface EmissionsUnitMapper {

    @Mapping(source = "facilitySite.id", target = "facilitySiteId")
    @Mapping(source = "emissionsProcesses", target = "emissionsProcesses")
    EmissionsUnitDto emissionsUnitToDto(EmissionsUnit emissionsUnit);
    
    List<EmissionsUnitDto> emissionsUnitsToEmissionUnitsDtos(List<EmissionsUnit> emissionUnits);
    
    EmissionsUnitPostOrderDto toUpDto(EmissionsUnit emissionsUnit);
    
    @Mapping(source = "facilitySiteId", target = "facilitySite.id")
    @Mapping(source = "emissionsProcesses", target = "emissionsProcesses")
    EmissionsUnit emissionsUnitFromDto(EmissionsUnitDto emissionsUnit);
    
    @Mapping(target = "operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    @Mapping(target = "unitOfMeasureCode", qualifiedByName  = "UnitMeasureCode")
    @Mapping(target = "unitTypeCode", qualifiedByName  = "UnitTypeCode")
    void updateFromDto(EmissionsUnitDto source, @MappingTarget EmissionsUnit target);

}