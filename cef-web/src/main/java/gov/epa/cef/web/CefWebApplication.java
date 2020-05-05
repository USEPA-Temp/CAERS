package gov.epa.cef.web;

import gov.epa.cef.web.config.YamlPropertySourceFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
    @PropertySource(factory = YamlPropertySourceFactory.class,
        value = "file:${spring.config.dir}/cef-web/cef-web-config.yml", ignoreResourceNotFound=true)
})
public class CefWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return configureApplication(builder);
    }

    public static void main(String[] args) {
        configureApplication(new SpringApplicationBuilder()).run(args);
    }

    private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(CefWebApplication.class);
    }
}
