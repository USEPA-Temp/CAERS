package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.AdminProperty;
import gov.epa.cef.web.service.dto.PropertyDto;

@Mapper(componentModel = "spring", uses = {})
public interface AppPropertyMapper {

    PropertyDto toDto(AdminProperty source);

    List<PropertyDto> toDtoList(List<AdminProperty> source);

    AdminProperty fromDto(PropertyDto source);
}
