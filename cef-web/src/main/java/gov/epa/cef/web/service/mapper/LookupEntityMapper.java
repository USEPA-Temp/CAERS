package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.common.BaseLookupEntity;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilityCategoryCodeDto;


@Mapper(componentModel = "spring", uses = {})
public interface LookupEntityMapper {

    CodeLookupDto toDto(BaseLookupEntity entity);

    CodeLookupDto naicsCodeToDto(NaicsCode code);

    FacilityCategoryCodeDto facilityCategoryCodeToDto(FacilityCategoryCode code);
}