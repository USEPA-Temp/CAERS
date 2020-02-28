package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.TestCategories;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;

@Category(TestCategories.EmbeddedDatabaseTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
@WithUserDetails(userDetailsServiceBeanName = "junitUserDetailsServiceImpl")
abstract class BaseServiceDatabaseTest {

    // marker
}
