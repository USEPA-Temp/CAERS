package gov.epa.cef.web.config;

import com.google.common.base.MoreObjects;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.Map;

@Component
@Validated
@ConfigurationProperties
public class NetworkNodeConfig {

    @NotEmpty
    private Map<NetworkNodeName, NodeEndpointConfig> networkNodes = new HashMap<>();

    public NodeEndpointConfig getNetworkNode(NetworkNodeName name) {

        return this.networkNodes.get(name);
    }

    public Map<NetworkNodeName, NodeEndpointConfig> getNetworkNodes() {

        return networkNodes;
    }

    public void setNetworkNodes(Map<NetworkNodeName, NodeEndpointConfig> networkNodes) {

        this.networkNodes.clear();
        if (networkNodes != null) {
            this.networkNodes.putAll(networkNodes);
        }
    }

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
            .add("networkNodes", networkNodes)
            .toString();
    }
}
