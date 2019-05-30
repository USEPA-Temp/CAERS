package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AircraftEngineTypeCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "aircraft_engine_type_code")

public class AircraftEngineTypeCode implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    // Fields
    
    @Id
    @Column(name = "code", unique = true, nullable = false, length = 10)
    private String code;
    
    @Column(name = "faa_aircraft_type", length = 50)
    private String faaAircraftType;
    
    @Column(name = "edms_accode", length = 15)
    private String edmsAccode;
    
    @Column(name = "engine_manufacturer")
    private String engineManufacturer;
    
    @Column(name = "engine", length = 70)
    private String engine;
    
    @Column(name = "edms_uid", length = 10)
    private String edmsUid;
    
    @Column(name = "scc", length = 10)
    private String scc;

    // Constructors

    /** default constructor */
    public AircraftEngineTypeCode() {
    }

    // Property accessors
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFaaAircraftType() {
        return this.faaAircraftType;
    }

    public void setFaaAircraftType(String faaAircraftType) {
        this.faaAircraftType = faaAircraftType;
    }

    public String getEdmsAccode() {
        return this.edmsAccode;
    }

    public void setEdmsAccode(String edmsAccode) {
        this.edmsAccode = edmsAccode;
    }

    public String getEngineManufacturer() {
        return this.engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getEdmsUid() {
        return this.edmsUid;
    }

    public void setEdmsUid(String edmsUid) {
        this.edmsUid = edmsUid;
    }

    public String getScc() {
        return this.scc;
    }

    public void setScc(String scc) {
        this.scc = scc;
    }

}