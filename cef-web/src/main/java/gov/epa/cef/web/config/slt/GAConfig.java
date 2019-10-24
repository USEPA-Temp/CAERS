package gov.epa.cef.web.config.slt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import gov.epa.cef.web.config.SLTBaseConfig;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ga")
public class GAConfig extends SLTBaseConfig{

}
