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

import gov.epa.cef.web.domain.AdminProperty;
import gov.epa.cef.web.domain.SLTConfigProperty;
import gov.epa.cef.web.service.dto.PropertyDto;

@Mapper(componentModel = "spring", uses = {})
public interface AppPropertyMapper {

    PropertyDto toDto(AdminProperty source);

    List<PropertyDto> toDtoList(List<AdminProperty> source);

    AdminProperty fromDto(PropertyDto source);

    @Mapping(source ="name.name", target="name")
    @Mapping(source ="name.label", target="label")
    @Mapping(source ="name.description", target="description")
    @Mapping(source ="name.datatype", target="datatype")
    PropertyDto sltToDto(SLTConfigProperty source);

    List<PropertyDto> sltToDtoList(List<SLTConfigProperty> source);

    SLTConfigProperty sltFromDto(PropertyDto source);
}
