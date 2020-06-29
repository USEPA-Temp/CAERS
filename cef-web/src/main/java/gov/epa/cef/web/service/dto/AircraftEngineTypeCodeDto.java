package gov.epa.cef.web.service.dto;

import java.io.Serializable;

public class AircraftEngineTypeCodeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;
	private String faaAircraftType;
	private String edmsAccode;
	private String engineManufacturer;
	private String engine;
	private String edmsUid;
	private String scc;
	private Integer lastInventoryYear;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFaaAircraftType() {
		return faaAircraftType;
	}

	public void setFaaAircraftType(String faaAircraftType) {
		this.faaAircraftType = faaAircraftType;
	}

	public String getEdmsAccode() {
		return edmsAccode;
	}

	public void setEdmsAccode(String edmsAccode) {
		this.edmsAccode = edmsAccode;
	}

	public String getEngineManufacturer() {
		return engineManufacturer;
	}

	public void setEngineManufacturer(String engineManufacturer) {
		this.engineManufacturer = engineManufacturer;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getEdmsUid() {
		return edmsUid;
	}

	public void setEdmsUid(String edmsUid) {
		this.edmsUid = edmsUid;
	}

	public String getScc() {
		return scc;
	}

	public void setScc(String scc) {
		this.scc = scc;
	}

    public Integer getLastInventoryYear() {
        return lastInventoryYear;
    }

    public void setLastInventoryYear(Integer lastInventoryYear) {
        this.lastInventoryYear = lastInventoryYear;
    }

}
