package gov.epa.cef.web.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cef", ignoreInvalidFields = true)
public class CefConfig {

	@Autowired
	protected CdxConfig cdxConfig;

	protected Map<String, String> cefExternalUrls;
	
	public CdxConfig getCdxConfig() {
		return cdxConfig;
	}

	public void setCdxConfig(CdxConfig cdxConfig) {
		this.cdxConfig = cdxConfig;
	}

	public Map<String, String> getCefExternalUrls() {
		return cefExternalUrls;
	}

	public void setCefExternalUrls(Map<String, String> cefExternalUrls) {
		this.cefExternalUrls = cefExternalUrls;
	}
}
