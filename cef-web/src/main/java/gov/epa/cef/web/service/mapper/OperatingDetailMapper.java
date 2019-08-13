package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.service.dto.OperatingDetailDto;



@Mapper(componentModel = "spring", uses = {})
public interface OperatingDetailMapper {
    
    OperatingDetailDto toDto(OperatingDetail source);

    void updateFromDto(OperatingDetailDto source, @MappingTarget OperatingDetail target);
}
