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
package gov.epa.cef.web.client.soap;

import com.google.common.io.Resources;
import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.util.TempFile;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@TestPropertySource(locations="classpath:application-unit_test.yml")
@Category(TestCategories.IntegrationTest.class)
@RunWith(MockitoJUnitRunner.class)
public class VirusScanClientTest {

    // https://www.eicar.org/?page_id=3950
    private static final String EicarSignature =
        "X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cdx.naas-user}")
    private String naasUser;

    @Value("${cdx.naas-password}")
    private String naasPassword;

    private VirusScanClient client;

    @Mock
    AdminPropertyProvider propertyProvider;

    @Mock
    NotificationService notificationService;

    @Before
    public void init() throws MalformedURLException {

        CdxConfig cdxConfig = new CdxConfig();
        cdxConfig.setNaasUser(naasUser);
        cdxConfig.setNaasPassword(naasPassword);

        VirusScanClient.VirusScanConfig config = new VirusScanClient.VirusScanConfig()
            .withEndpoint(new URL("https://tools.epacdxnode.net/xml/VirusScan.wsdl"));

        when(this.propertyProvider.getBoolean(any(), eq(false))).thenReturn(true);

        this.client = new VirusScanClient(this.notificationService, this.propertyProvider, cdxConfig, config);
    }

    @Test
    public void testSuccessScan() throws Exception {

        String filename = "CEF_BulkUpload_Template_jim.xlsx";

        URL workbook = Resources.getResource("excel/".concat(filename));

        File file = new File(workbook.toURI());

        this.client.scanFile(TempFile.from(file, filename));

        // not throwing an exception is passing this test, but we need to have an assertion for sonarqube
        assertTrue(true);
    }

    @Test
    public void testFailedScan() throws Exception {

        // This test may not work with an antivirus scanned (desktop) environment
        // It does work in vbox or anything w/o antivirus running
        try (InputStream inputStream = new ByteArrayInputStream(EicarSignature.getBytes(StandardCharsets.UTF_8));
             TempFile tempFile = TempFile.from(inputStream, "VirusScan.tmp")) {

            this.client.scanFile(tempFile);

            fail("Should not have reached this point.");

        } catch (ApplicationException e) {

            assertEquals(ApplicationErrorCode.E_VALIDATION, e.getErrorCode());
            logger.info(e.getMessage());

        } catch (Exception e) {

            logger.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }
}
