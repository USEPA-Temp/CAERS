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

import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import net.exchangenetwork.schema.cer._1._2.AddressDataType;
import net.exchangenetwork.schema.cer._1._2.AffiliationDataType;
import net.exchangenetwork.schema.cer._1._2.CommunicationDataType;
import net.exchangenetwork.schema.cer._1._2.FacilityIdentificationDataType;
import net.exchangenetwork.schema.cer._1._2.FacilityNAICSDataType;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;
import net.exchangenetwork.schema.cer._1._2.GeographicCoordinatesDataType;
import net.exchangenetwork.schema.cer._1._2.IndividualDataType;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CersEmissionsUnitMapper.class, CersReleasePointMapper.class})
public interface CersFacilitySiteMapper {

    @Mapping(source="facilityCategoryCode.code", target="facilityCategoryCode")
    @Mapping(source="name", target="facilitySiteName")
    @Mapping(source="description", target="facilitySiteDescription")
    @Mapping(source="operatingStatusCode.code", target="facilitySiteStatusCode")
    @Mapping(source="statusYear", target="facilitySiteStatusCodeYear")
    @Mapping(source="facilityNAICS", target="facilityNAICS")
    @Mapping(source=".", target="facilityIdentification")
    @Mapping(source=".", target="facilitySiteAddress")
    @Mapping(source=".", target="facilitySiteGeographicCoordinates")
    @Mapping(source="comments", target="facilitySiteComment")
    @Mapping(source="emissionsUnits", target="emissionsUnit")
    @Mapping(source="releasePoints", target="releasePoint")
    FacilitySiteDataType fromFacilitySite(FacilitySite source);

    @Mapping(source="naicsCode.code", target="NAICSCode")
    FacilityNAICSDataType cersNaicsFromFacilityNAICSXref(FacilityNAICSXref source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="altSiteIdentifier", target="facilitySiteIdentifier")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    @Mapping(source="countyCode.code", target="stateAndCountyFIPSCode")
    @Mapping(source="tribalCode.code", target="tribalCode")
    FacilityIdentificationDataType facilityIdentificationFromFacilitySite(FacilitySite source);

    @Mapping(source="latitude", target="latitudeMeasure")
    @Mapping(source="longitude", target="longitudeMeasure")
    GeographicCoordinatesDataType facilityGeographicCoordinatesFromFacilitySite(FacilitySite source);

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

    @Mapping(source="contacts", target="affiliationIndividual")
    AffiliationDataType facilitySiteAffiliation(FacilitySite source);

    @Mapping(source="type.code", target="individualTitleText")
    @Mapping(source="prefix", target="namePrefixText")
    @Mapping(source="firstName", target="firstName")
    @Mapping(source="lastName", target="lastName")
    @Mapping(source=".", target="individualAddress")
    @Mapping(source=".", target="individualCommunication")
    IndividualDataType facilitySiteContact(FacilitySiteContact source);

    @Mapping(source="streetAddress", target="locationAddressText")
    @Mapping(source="city", target="localityName")
    @Mapping(source="stateCode.uspsCode", target="locationAddressStateCode")
    @Mapping(source="postalCode", target="locationAddressPostalCode")
    @Mapping(source="countryCode", target="locationAddressCountryCode")
    @Mapping(source="mailingStreetAddress", target="mailingAddressText")
    @Mapping(source="mailingCity", target="mailingAddressCityName")
    @Mapping(source="mailingStateCode.uspsCode", target="mailingAddressStateCode")
    @Mapping(source="mailingPostalCode", target="mailingAddressPostalCode")
    AddressDataType addressFromFacilitySiteContact(FacilitySiteContact source);

    @Mapping(source="phone", target="telephoneNumberText")
    @Mapping(source="phoneExt", target="telephoneExtensionNumberText")
    @Mapping(source="email", target="electronicAddressText")
    CommunicationDataType contactCommunicationFacilitySiteContact(FacilitySiteContact source);

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

    default List<AffiliationDataType> contactListFromFacilitySite(FacilitySite source) {
      if (source == null) {
          return Collections.emptyList();
      }
      return Collections.singletonList(facilitySiteAffiliation(source));
    }

    default List<AddressDataType> addressListFromFacilitySiteContact(FacilitySiteContact source) {
      if (source == null) {
          return Collections.emptyList();
      }
      return Collections.singletonList(addressFromFacilitySiteContact(source));
    }

    default List<CommunicationDataType> contactCommunicationListFromFacilitySiteContact(FacilitySiteContact source) {
      if (source == null) {
          return Collections.emptyList();
      }
      return Collections.singletonList(contactCommunicationFacilitySiteContact(source));
    }

}
