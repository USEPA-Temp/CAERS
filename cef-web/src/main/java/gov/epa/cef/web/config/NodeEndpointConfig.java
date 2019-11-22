package gov.epa.cef.web.config;

import com.google.common.base.MoreObjects;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;

@Validated
public class NodeEndpointConfig {

    @NotBlank
    private String dataflow;

    @NotNull
    private URL serviceUrl;

    public String getDataflow() {

        return dataflow;
    }

    public void setDataflow(String dataflow) {

        this.dataflow = dataflow;
    }

    public URL getServiceUrl() {

        return serviceUrl;
    }

    public void setServiceUrl(URL serviceUrl) {

        this.serviceUrl = serviceUrl;
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
            .add("dataflow", dataflow)
            .add("serviceUrl", serviceUrl)
            .toString();
    }
}
