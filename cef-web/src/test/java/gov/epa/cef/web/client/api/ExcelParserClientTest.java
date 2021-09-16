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
package gov.epa.cef.web.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import gov.epa.cef.web.client.soap.SecurityTokenClient;
import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.config.TestCategories;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

@TestPropertySource(locations="classpath:application-unit_test.yml")
@Category(TestCategories.IntegrationTest.class)
public class ExcelParserClientTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cdx.naas-user}")
    private String naasUser;

    @Value("${cdx.naas-password}")
    private String naasPassword;

    @Value("${naas-token-url}")
    private String naasTokenUrl;

    private ExcelParserClient client;

    @Before
    public void init() throws MalformedURLException {

        ExcelParserClient.ExcelParserClientConfig config = new ExcelParserClient.ExcelParserClientConfig()
            .withBaseUrl(new URL("http://localhost:8085/excel-json-parser/api/v2"));

        CdxConfig cdxConfig = new CdxConfig();
        cdxConfig.setNaasUser(this.naasUser);
        cdxConfig.setNaasPassword(this.naasPassword);
        cdxConfig.setNaasTokenUrl(new URL(this.naasTokenUrl));

        this.client = new ExcelParserClient(config, new SecurityTokenClient(cdxConfig), new ObjectMapper());
    }

    @Test
    public void simpleParseTest() throws Exception {

        String filename = "CEF_BulkUpload_Template_jim.xlsx";

        URL workbook = Resources.getResource("excel/".concat(filename));

        File file = new File(workbook.toURI());

        JsonNode json = this.client.parseWorkbook(filename, file).getJson();

        logger.debug(json.toString());

        assertNotNull(json);
    }
}
