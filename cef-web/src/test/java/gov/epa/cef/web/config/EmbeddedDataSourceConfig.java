package gov.epa.cef.web.config;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Collections;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

@Profile("unit_test")
@Configuration
@EnableTransactionManagement
public class EmbeddedDataSourceConfig {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedDataSourceConfig.class);

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUserName;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;
    
    @Bean(destroyMethod = "stop")
    public EmbeddedPostgres postgresProcess() throws IOException {
        LOGGER.info("Starting embedded Postgres");
        URI uri = URI.create(dataSourceUrl.substring(5));
        EmbeddedPostgres process = new EmbeddedPostgres(Version.V10_6);
        process.start(EmbeddedPostgres.cachedRuntimeConfig(Paths.get("target")),
                        uri.getHost(), uri.getPort(), uri.getPath().substring(1),
                        dataSourceUserName,
                        dataSourcePassword,
                        Collections.emptyList()
                        );

        return process;
    }
    
    @Bean(destroyMethod = "close")
    @DependsOn("postgresProcess")
    DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }   
    
}
