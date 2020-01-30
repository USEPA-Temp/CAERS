package gov.epa.cef.web.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import gov.epa.cef.web.util.TempFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.UUID;

@Component
//@ConstructorBinding
@Valid
@ConfigurationProperties(prefix = "excel-parser")
public class ExcelParserClient {

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @NotNull
    private URL baseUrl;

    @Autowired
    ExcelParserClient(ObjectMapper objectMapper) {

        this.objectMapper = objectMapper;
    }

    public URL getBaseUrl() {

        return baseUrl;
    }

    public void setBaseUrl(URL baseUrl) {

        this.baseUrl = baseUrl;
    }

    public JsonNode parseWorkbook(TempFile tempFile) {

        return parseWorkbook(tempFile.getFileName(), tempFile.getFile());
    }

    public JsonNode parseWorkbook(String filename, File workbook) {

        JsonNode result = null;

        URL url = makeUrl("/parse");
        HttpURLConnection connection = null;

        try {

            connection = (HttpURLConnection) url.openConnection();

            String boundary = UUID.randomUUID().toString();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=".concat(boundary));

            try (DataOutputStream request = new DataOutputStream(connection.getOutputStream())) {

                String boundaryBegLine = String.format("--%s\r\n", boundary);
                String boundaryEndLine = String.format("\r\n--%s--\r\n", boundary);

                request.writeBytes(boundaryBegLine);
                request.writeBytes(String.format(
                    "Content-Disposition: form-data; name=\"workbook\"; filename=\"%s\"\r\n\r\n",
                    filename));

                try (InputStream fileStream = new FileInputStream(workbook)) {

                    ByteStreams.copy(fileStream, request);
                }

                request.writeBytes(boundaryEndLine);

                request.flush();
            }

            int responseCode = connection.getResponseCode();
            logger.debug("Parser returned response code {}", responseCode);

            try (InputStream inputStream = connection.getInputStream()) {

                result = this.objectMapper.readTree(inputStream);
            }

        } catch (IOException e) {

            throw new IllegalStateException(e);

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }

        return result;
    }

    public ExcelParserClient withBaseUrl(final URL baseUrl) {

        setBaseUrl(baseUrl);
        return this;
    }

    private URL makeUrl(String path) {

        URL result = null;

        try {

            result = new URL(UriComponentsBuilder
                .fromUri(this.baseUrl.toURI())
                .path(path)
                .toUriString());

        } catch (URISyntaxException | MalformedURLException e) {

            throw new IllegalArgumentException(e);
        }

        return result;
    }
}
