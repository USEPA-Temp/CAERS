package gov.epa.cef.web.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"gov.epa.cef.web.repository"})
@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
public class AppConfig {

    private final DataSource dataSource;

    @Autowired
    AppConfig(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception {

        return new JdbcTemplate(this.dataSource);
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        return Flyway.configure()
            .locations("classpath:db/migrations", "classpath:gov/epa/cef/infrastructure/persistence/migrations")
            .table("schema_version_cef")
            .baselineOnMigrate(true)
            .baselineVersion("0.0.1")
            .dataSource(this.dataSource)
            .load();
    }
}
