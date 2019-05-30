package gov.epa.cef.web.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * OperatingStatusCode entity
 */
@Entity
@Table(name = "operating_status_code")
public class OperatingStatusCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;

    @Column(name = "description", length = 200)
    private String description;


    /** default constructor */
    public OperatingStatusCode() {
    }

    
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}