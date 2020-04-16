package gov.epa.cef.web.service.mapper.cers;

import java.util.Collections;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import net.exchangenetwork.schema.cer._1._2.AddressDataType;
import net.exchangenetwork.schema.cer._1._2.FacilityIdentificationDataType;
import net.exchangenetwork.schema.cer._1._2.FacilityNAICSDataType;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;

@Mapper(componentModel = "spring", uses = {CersEmissionsUnitMapper.class, CersReleasePointMapper.class})
public interface CersFacilitySiteMapper {

    @Mapping(source="facilityCategoryCode.code", target="facilityCategoryCode")
    @Mapping(source="name", target="facilitySiteName")
    @Mapping(source="description", target="facilitySiteDescription")
    @Mapping(source="operatingStatusCode.code", target="facilitySiteStatusCode")
    @Mapping(source="statusYear", target="facilitySiteStatusCodeYear")
    @Mapping(source="facilityNAICS", target="facilityNAICS")
    @Mapping(source=".", target="facilityIdentification")
    @Mapping(source=".", target="facilitySiteAddress")
    @Mapping(source="emissionsUnits", target="emissionsUnit")
    @Mapping(source="releasePoints", target="releasePoint")
    FacilitySiteDataType fromFacilitySite(FacilitySite source);

    @Mapping(source="naicsCode.code", target="NAICSCode")
    @Mapping(source="primaryFlag", target="NAICSPrimaryIndicator")
    FacilityNAICSDataType cersNaicsFromFacilityNAICSXref(FacilityNAICSXref source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="eisProgramId", target="facilitySiteIdentifier")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    FacilityIdentificationDataType facilityIdentificationFromFacilitySite(FacilitySite source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="streetAddress", target="locationAddressText")
    @Mapping(source="city", target="localityName")
    @Mapping(source="stateCode.uspsCode", target="locationAddressStateCode")
    @Mapping(source="postalCode", target="locationAddressPostalCode")
    @Mapping(source="countryCode", target="locationAddressCountryCode")
    @Mapping(source="mailingStreetAddress", target="mailingAddressText")
    @Mapping(source="mailingCity", target="mailingAddressCityName")
    @Mapping(source="mailingStateCode.uspsCode", target="mailingAddressStateCode")
    @Mapping(source="mailingPostalCode", target="mailingAddressPostalCode")
    AddressDataType addressFromFacilitySite(FacilitySite source);

    default List<FacilityIdentificationDataType> facilityIdentificationListFromFacilitySite(FacilitySite source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(facilityIdentificationFromFacilitySite(source));
    }

    default List<AddressDataType> addressListFromFacilitySite(FacilitySite source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(addressFromFacilitySite(source));
    }
}
