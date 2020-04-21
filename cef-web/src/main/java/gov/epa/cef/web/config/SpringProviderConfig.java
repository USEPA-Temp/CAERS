package gov.epa.cef.web.config;

import gov.epa.cef.web.provider.system.PropertyProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class SpringProviderConfig {

    private final NamedParameterJdbcTemplate jdbcTemplate;


    @Autowired
    SpringProviderConfig(NamedParameterJdbcTemplate jdbcTemplate) {

        super();

        this.jdbcTemplate = jdbcTemplate;
    }

    @Bean
    public PropertyProvider propertyProvider() {

        return new PropertyProvider(this.jdbcTemplate, "admin_properties");
    }

}
