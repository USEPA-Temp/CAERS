package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.service.mapper.cers.CersFacilitySiteMapper;
import gov.epa.cef.web.service.mapper.cers.CersFacilitySiteMapperImpl;
import net.exchangenetwork.schema.cer._1._2.FacilitySiteDataType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

@Category(TestCategories.FastTest.class)
public class CersXmlServiceImlTest {

    @Test
    public void noFacilitySiteAffiliationTest() throws Exception {

        FacilitySite facilitySite = new FacilitySite();

        FacilitySiteContact contact = new FacilitySiteContact();
        contact.setFirstName("Fred");
        contact.setLastName("Flintstone");

        facilitySite.setContacts(Collections.singletonList(contact));

        CersFacilitySiteMapper cersFacilitySiteMapper = new CersFacilitySiteMapperImpl();

        FacilitySiteDataType facilitySiteDataType = cersFacilitySiteMapper.fromFacilitySite(facilitySite);

        assertTrue(facilitySiteDataType.getFacilitySiteAffiliation().isEmpty());
    }
}
