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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * UnitMeasureCode entity
 */
@Entity
@Table(name = "unit_measure_code")
public class UnitMeasureCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code", unique = true, nullable = false, length = 20)
    private String code;
    
    @Column(name = "description", length = 100)
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "unitOfMeasureCode")
    @JsonIgnore
    private Set<EmissionsUnit> emissionsunits = new HashSet<EmissionsUnit>(0);


    /** default constructor */
    public UnitMeasureCode() {
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