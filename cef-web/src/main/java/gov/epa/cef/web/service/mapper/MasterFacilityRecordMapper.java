package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface MasterFacilityRecordMapper {

    @Mapping(target="countyCode", qualifiedByName = "CountyCode")
    @Mapping(target="stateCode", qualifiedByName = "FipsStateCode")
    @Mapping(target="mailingStateCode", qualifiedByName = "FipsStateCode")
    @Mapping(target="tribalCode", qualifiedByName = "TribalCode")
    @Mapping(target ="operatingStatusCode", qualifiedByName  = "OperatingStatusCode")
    @Mapping(target = "facilityCategoryCode", qualifiedByName = "FacilityCategoryCode")
    @Mapping(target = "facilitySourceTypeCode", qualifiedByName = "FacilitySourceTypeCode")
    void updateFromDto(MasterFacilityRecordDto source, @MappingTarget MasterFacilityRecord target);

    @Mapping(target = "id", ignore = true)
    @Mapping(source="altSiteIdentifier", target="agencyFacilityId")
    MasterFacilityRecordDto facilitySiteDtoToDto(FacilitySiteDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(source="agencyFacilityId", target="altSiteIdentifier")
    FacilitySite toFacilitySite(MasterFacilityRecord source);

    @Mapping(target = "id", ignore = true)
    @Mapping(source="altSiteIdentifier", target="agencyFacilityId")
    MasterFacilityRecord fromFacilitySite(FacilitySite source);

    @Mapping(target = "id", ignore = true)
    @Mapping(source="agencyFacilityId", target="altSiteIdentifier")
    void updateFacilitySite(MasterFacilityRecord source, @MappingTarget FacilitySite target);
}
