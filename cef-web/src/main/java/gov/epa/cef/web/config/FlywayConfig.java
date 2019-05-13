package gov.epa.cef.web.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUserName;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
            .locations("classpath:db/migrations", "classpath:gov/epa/cef/infrastructure/persistence/migrations")
            .table("schema_version_cef")
            .baselineOnMigrate(true)
            .baselineVersion("0.0.1")
            .dataSource(dataSourceUrl, dataSourceUserName, dataSourcePassword)
            .load();
    }
    
}