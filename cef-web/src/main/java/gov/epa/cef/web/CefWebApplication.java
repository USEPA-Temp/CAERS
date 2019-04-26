package gov.epa.cef.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({
	@PropertySource(value = "classpath:application.yml"),
	@PropertySource(value = "file:${spring.config.dir}/cef-web/cef-web-config.yml", ignoreResourceNotFound=true),
})
public class CefWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CefWebApplication.class, args);
	}

}
