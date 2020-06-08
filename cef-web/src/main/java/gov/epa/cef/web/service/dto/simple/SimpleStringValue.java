package gov.epa.cef.web.service.dto.simple;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

public class SimpleStringValue {

    private final String value;

    @JsonCreator
    public SimpleStringValue(@JsonProperty("value") String value) {

        this.value = Strings.emptyToNull(value);
    }

    public String getValue() {

        return value;
    }
}
