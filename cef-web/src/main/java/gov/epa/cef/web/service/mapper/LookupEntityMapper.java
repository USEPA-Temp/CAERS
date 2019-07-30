package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.common.BaseLookupEntity;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilityCategoryCodeDto;


@Mapper(componentModel = "spring", uses = {})
public interface LookupEntityMapper {

    CodeLookupDto toDto(BaseLookupEntity source);

    List<CodeLookupDto> toDtoList(List<BaseLookupEntity> source);

    CodeLookupDto naicsCodeToDto(NaicsCode code);

    CodeLookupDto reportingPeriodCodeToDto(ReportingPeriodCode source);

    FacilityCategoryCodeDto facilityCategoryCodeToDto(FacilityCategoryCode code);
}