package gov.epa.cef.web.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
public class FlywayConfig {

    private final DataSource dataSource;

    @Autowired
    FlywayConfig(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return Flyway.configure()
            .locations("classpath:db/migrations", "classpath:gov/epa/cef/infrastructure/persistence/migrations")
            .table("schema_version_cef")
            .baselineOnMigrate(true)
            .baselineVersion("0.0.1")
            .dataSource(this.dataSource)
            .load();
    }

}
