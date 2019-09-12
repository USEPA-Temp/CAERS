package gov.epa.cef.web.client.api;

import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;

import java.util.function.Function;

public class FrsApiTransforms {

    public static Function<ProgramFacility, FacilitySite> toFacilitySite() {

        return pf -> {

            FacilitySite result = new FacilitySite();
            result.setFrsFacilityId(pf.getRegistryId());
            result.setEisProgramId(pf.getProgramSystemId());
            result.setName(pf.getPrimaryName());
            result.setStreetAddress(pf.getLocationAddress());
            result.setCity(pf.getCityName());
            result.setPostalCode(pf.getPostalCode());
            result.setCounty(pf.getCountyName());
            result.setCountryCode(pf.getCountryISO31661Alpha2());

            result.setStatusYear(pf.getOperatingStatusYear() == null ? null : pf.getOperatingStatusYear().shortValue());

            OperatingStatusCode statusCode = new OperatingStatusCode();
            statusCode.setCode(pf.getOperatingStatus() == null ? "OP" : pf.getOperatingStatus());
            result.setOperatingStatusCode(statusCode);

            ProgramSystemCode programSystemCode = new ProgramSystemCode();
            programSystemCode.setCode(pf.getFacilitySourceSystemProgramCode());
            result.setProgramSystemCode(programSystemCode);

            // TODO lacks precision
            result.setLatitude(pf.getLatitude() == null ? 0d : pf.getLatitude().doubleValue());
            result.setLongitude(pf.getLongitude() == null ? 0d : pf.getLatitude().doubleValue());


            return result;
        };
    }

    public static Function<Contact, FacilitySiteContact> toFacilitySiteContact(FacilitySite fs) {

        return c -> {

            FacilitySiteContact result = new FacilitySiteContact();
            result.setFacilitySite(fs);
            result.setEmail(c.getEmailAddress());
            result.setPhone(c.getPhoneNumber());
            result.setPhoneExt(c.getPhoneNumberExtension());

            result.setStreetAddress(c.getMailingAddress());
            result.setCity(c.getCityName());
            result.setCounty(c.getCountyName());
            result.setCountryCode(c.getCountryName());

            return result;
        };
    }

    public static Function<Naics, FacilityNAICSXref> toFacilityNaicsXref(FacilitySite fs) {

        return naics -> {

            FacilityNAICSXref result = new FacilityNAICSXref();
            result.setFacilitySite(fs);
            result.setPrimaryFlag("PRIMARY".equals(naics.getPrimaryIndicator()));

            NaicsCode naicsCode =  new NaicsCode();
            naicsCode.setCode(Integer.valueOf(naics.getNaicsCode()));

            result.setNaicsCode(naicsCode);

            return result;
        };
    }
}
