package gov.epa.cef.web.client.api;

import com.fasterxml.jackson.databind.JsonNode;

public class ExcelParserResponse {

    private final JsonNode json;

    private final int responseCode;

    public ExcelParserResponse(int responseCode, JsonNode json) {

        this.responseCode = responseCode;
        this.json = json;
    }

    public JsonNode getJson() {

        return json;
    }

    public int getResponseCode() {

        return responseCode;
    }
}
