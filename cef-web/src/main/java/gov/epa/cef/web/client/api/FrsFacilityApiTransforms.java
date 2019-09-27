package gov.epa.cef.web.client.api;

import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

import static gov.epa.cef.web.client.api.FrsUtil.createOperatingStatus;

public class FrsFacilityApiTransforms {

    private static final Logger logger = LoggerFactory.getLogger(FrsFacilityApiTransforms.class);

    public static Function<ProgramFacility, FacilitySite> toFacilitySite(EmissionsReport report) {

        return pf -> {

            FacilitySite result = new FacilitySite();
            result.setEmissionsReport(report);
            result.setFrsFacilityId(pf.getRegistryId());
            result.setEisProgramId(pf.getProgramSystemId());
            result.setName(pf.getPrimaryName());
            result.setStreetAddress(pf.getLocationAddress());
            result.setCity(pf.getCityName());
            result.setStateCode(pf.getStateCode());
            result.setPostalCode(pf.getPostalCode());
            result.setCounty(pf.getCountyName());
            result.setCountryCode(pf.getCountryISO31661Alpha2());

            result.setStatusYear(pf.getOperatingStatusYear() == null ? null : pf.getOperatingStatusYear().shortValue());

            result.setOperatingStatusCode(createOperatingStatus(pf.getOperatingStatus()));

            String pscode = pf.getFacilitySourceSystemProgramCode();
            if (StringUtils.isNotBlank(pscode)) {

                ProgramSystemCode programSystemCode = new ProgramSystemCode();
                programSystemCode.setCode(pscode);
                result.setProgramSystemCode(programSystemCode);
            }

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
