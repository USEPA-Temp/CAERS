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

    // Fields

    private String code;
    private String faaAircraftType;
    private String edmsAccode;
    private String engineManufacturer;
    private String engine;
    private String edmsUid;
    private String scc;

    // Constructors

    /** default constructor */
    public AircraftEngineTypeCode() {
    }

    /** minimal constructor */
    public AircraftEngineTypeCode(String code) {
        this.code = code;
    }

    /** full constructor */
    public AircraftEngineTypeCode(String code, String faaAircraftType, String edmsAccode, String engineManufacturer,
            String engine, String edmsUid, String scc) {
        this.code = code;
        this.faaAircraftType = faaAircraftType;
        this.edmsAccode = edmsAccode;
        this.engineManufacturer = engineManufacturer;
        this.engine = engine;
        this.edmsUid = edmsUid;
        this.scc = scc;
    }

    // Property accessors
    @Id

    @Column(name = "code", unique = true, nullable = false, length = 10)

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "faa_aircraft_type", length = 50)

    public String getFaaAircraftType() {
        return this.faaAircraftType;
    }

    public void setFaaAircraftType(String faaAircraftType) {
        this.faaAircraftType = faaAircraftType;
    }

    @Column(name = "edms_accode", length = 15)

    public String getEdmsAccode() {
        return this.edmsAccode;
    }

    public void setEdmsAccode(String edmsAccode) {
        this.edmsAccode = edmsAccode;
    }

    @Column(name = "engine_manufacturer")

    public String getEngineManufacturer() {
        return this.engineManufacturer;
    }

    public void setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
    }

    @Column(name = "engine", length = 70)

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    @Column(name = "edms_uid", length = 10)

    public String getEdmsUid() {
        return this.edmsUid;
    }

    public void setEdmsUid(String edmsUid) {
        this.edmsUid = edmsUid;
    }

    @Column(name = "scc", length = 10)

    public String getScc() {
        return this.scc;
    }

    public void setScc(String scc) {
        this.scc = scc;
    }

}