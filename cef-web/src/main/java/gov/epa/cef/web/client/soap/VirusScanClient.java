package gov.epa.cef.web.client.soap;

import gov.epa.cef.web.config.CdxConfig;
import gov.epa.cef.web.exception.VirusScanException;
import gov.epa.cef.web.provider.system.IPropertyKey;
import gov.epa.cef.web.provider.system.PropertyProvider;
import gov.epa.cef.web.util.TempFile;
import net.exchangenetwork.wsdl.virusscan._1.ScanFilePortType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.UUID;

@Component
public class VirusScanClient extends AbstractClient {

    private static final String CleanFile = "The file is clean";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final PropertyProvider propertyProvider;

    private final CdxConfig cdxConfig;

    private final VirusScanConfig virusScanConfig;

    @Autowired
    VirusScanClient(PropertyProvider propertyProvider, CdxConfig cdxConfig, VirusScanConfig virusScanConfig) {

        this.propertyProvider = propertyProvider;
        this.cdxConfig = cdxConfig;
        this.virusScanConfig = virusScanConfig;
    }

    public boolean isEnabled() {

        return this.propertyProvider.getBoolean(VirusScanProperty.VirusScannerEnabled, false);
    }

    public void scanFile(TempFile tempFile) {

        if (isEnabled()) {

            doScanFile(tempFile);
        }
    }

    private void doScanFile(TempFile tempFile) {

        ScanFilePortType virusScanner = getClient(this.virusScanConfig.getEndpoint(),
            ScanFilePortType.class, true, true);

        UUID transId = UUID.randomUUID();

        String result = "Waiting for response...";
        try {

            result = virusScanner.scanFile(this.cdxConfig.getNaasUser(), this.cdxConfig.getNaasPassword(),
                "default", transId.toString(), new DataHandler(new FileDataSource(tempFile.getFile())));

        } catch (Exception e) {

            // log the exception we are about to eat
            logger.warn("Eating exception", e);

            result = e.getMessage();
            throw new VirusScanException(e.getMessage());

        } finally {

            logger.debug("VirusScan {} returned: {}", transId, result);
        }

        if (CleanFile.equals(result) == false) {

            // not clean
            String message = String.format("Virus scan transId %s returned result: %s", transId.toString(), result);
            throw new VirusScanException(message);
        }
    }

    private static enum VirusScanProperty implements IPropertyKey {

        VirusScannerEnabled("virus-scanner.enabled");

        private final String configKey;

        VirusScanProperty(String configKey) {

            this.configKey = configKey;
        }

        @Override
        public String configKey() {

            return this.configKey;
        }
    }

    @Component
    @Validated
    @ConfigurationProperties(prefix = "virus-scanner")
    public static class VirusScanConfig {

        @NotNull
        private URL endpoint;

        public URL getEndpoint() {

            return endpoint;
        }

        public void setEndpoint(URL endpoint) {

            this.endpoint = endpoint;
        }

        public VirusScanConfig withEndpoint(final URL endpoint) {

            setEndpoint(endpoint);
            return this;
        }
    }
}
