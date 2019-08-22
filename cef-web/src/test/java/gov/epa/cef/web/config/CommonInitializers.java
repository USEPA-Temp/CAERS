package gov.epa.cef.web.config;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class CommonInitializers {

    public static class NoCacheInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.jpa.properties.hibernate.cache.use_second_level_cache=false",
                "spring.jpa.properties.hibernate.cache.use_query_cache=false"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
