package gov.epa.cef.web.client.api;

import gov.epa.cef.web.service.dto.SccResourceSearchApiDto;
import gov.epa.client.sccwebservices.ApiClient;
import gov.epa.client.sccwebservices.SccSearchApiApi;
import gov.epa.client.sccwebservices.model.SccDetail;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.sun.istack.NotNull;

@Component
public class SccApiClient {

    private final SccSearchApiApi client;

    private final SccConfig config;

    @Autowired
    SccApiClient(SccConfig config) {

        super();

        this.config = config;

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(this.config.getSccwebservicesEndpoint().toString());

        this.client = new SccSearchApiApi(apiClient);
    }

    public List<SccDetail> getResourceSearchResults(SccResourceSearchApiDto dto) {

        return this.client.getResourceSearchResults(dto.getFacetName(), dto.getFacetValue(), 
                dto.getFacetQualifier(), dto.getFacetMatchType(), dto.getParamCallback(), dto.getFormat(), 
                dto.getFilename(), dto.getLastUpdatedSinceString(), dto.getSortFacet(), dto.getPageNum(), dto.getPageSize());
    }


    @Component
    @Validated
    @ConfigurationProperties(prefix = "scc")
    public static class SccConfig {

        @NotNull
        private URL sccwebservicesEndpoint;

        public URL getSccwebservicesEndpoint() {
            return sccwebservicesEndpoint;
        }

        public void setSccwebservicesEndpoint(URL sccwebservicesEndpoint) {
            this.sccwebservicesEndpoint = sccwebservicesEndpoint;
        }


    }
}
