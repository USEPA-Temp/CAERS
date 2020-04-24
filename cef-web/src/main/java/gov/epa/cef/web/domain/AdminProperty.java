package gov.epa.cef.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "admin_properties")
public class AdminProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name", unique = true, nullable = false, length = 64)
    protected String name;

    @Column(name = "value", length = 255)
    protected String value;

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

}