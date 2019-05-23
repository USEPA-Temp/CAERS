package gov.epa.cef.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import gov.epa.cef.web.config.EmbeddedDataSourceConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EmbeddedDataSourceConfig.class})
@SpringBootTest
@ActiveProfiles(profiles = "unit_test")
public class CefWebApplicationTests {

	@Test
	public void contextLoads() {
	}

}
