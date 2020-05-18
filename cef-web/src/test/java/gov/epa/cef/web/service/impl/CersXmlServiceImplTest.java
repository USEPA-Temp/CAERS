package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.service.mapper.cers.CersEmissionsUnitMapper;
import gov.epa.cef.web.service.mapper.cers.CersEmissionsUnitMapperImpl;
import net.exchangenetwork.schema.cer._1._2.EmissionsDataType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@Category(TestCategories.FastTest.class)
public class CersXmlServiceImplTest {

    @Test
    public void totalEmissionsRoundingTest() {

        CersEmissionsUnitMapper emissionsUnitMapper = new CersEmissionsUnitMapperImpl();

        Emission emission = new Emission();
        emission.setTotalEmissions(new BigDecimal("7323.234258245252345"));

        EmissionsDataType emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234258", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234258745252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234259", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234257745252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234258", emissionsDataType.getTotalEmissions());

        emission.setTotalEmissions(new BigDecimal("7323.234257345252345"));
        emissionsDataType = emissionsUnitMapper.emissionsFromEmission(emission);
        assertEquals("7323.234257", emissionsDataType.getTotalEmissions());
    }
}
