package gov.epa.cef.web.service.dto;

import java.io.Serializable;

import gov.epa.cef.web.provider.system.IPropertyKey;

public class PropertyDto implements IPropertyKey, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String value;

    public PropertyDto() {};

    public PropertyDto(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String configKey() {
        return name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public PropertyDto withName(final String name) {

        setName(name);
        return this;
    }

    public PropertyDto withValue(final String value) {

        setValue(value);
        return this;
    }

}
