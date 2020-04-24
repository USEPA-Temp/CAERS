package gov.epa.cef.web.service.dto.bulkUpload;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

public class BlankToNullModule extends SimpleModule {

    public BlankToNullModule() {

        addDeserializer(String.class, new BlankToNullDeserializer());
    }

    private static class BlankToNullDeserializer extends StdDeserializer<String> {

        public BlankToNullDeserializer() {

            super(String.class);
        }

        @Override
        public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

            JsonNode node = jsonParser.readValueAsTree();
            if (node.asText().isEmpty()) {
                return null;
            }

            return node.asText();
        }
    }
}
