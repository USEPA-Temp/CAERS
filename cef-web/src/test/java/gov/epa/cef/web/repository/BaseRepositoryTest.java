package gov.epa.cef.web.repository;

import gov.epa.cdx.shared.security.ApplicationUser;
import gov.epa.cef.web.config.TestCategories;
import gov.epa.cef.web.security.AppRole;
import gov.epa.cef.web.security.SecurityService;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@Category(TestCategories.EmbeddedDatabaseTest.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureEmbeddedDatabase
abstract class BaseRepositoryTest {

    @Autowired
    SecurityService securityService;

    void runWithMockUser() {

        ApplicationUser applicationUser = new ApplicationUser("FFLINTSTONE",
            Collections.singletonList(new SimpleGrantedAuthority(AppRole.ROLE_PREPARER)));

        this.securityService.asUser(applicationUser);
    }
}
