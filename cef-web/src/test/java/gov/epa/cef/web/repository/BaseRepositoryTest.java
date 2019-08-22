package gov.epa.cef.web.repository;

import gov.epa.cef.web.config.TestCategories;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

@Profile("unit_test")
@Category(TestCategories.EmbeddedDatabaseTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
public abstract class BaseRepositoryTest {

}
