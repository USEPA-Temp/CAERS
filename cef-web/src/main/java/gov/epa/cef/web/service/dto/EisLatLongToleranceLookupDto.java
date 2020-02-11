package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class EisLatLongToleranceLookupDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String eisProgramId;
	private BigDecimal coordinateTolerance;
	
	public String getEisProgramId() {
		return eisProgramId;
	}
	public void setEisProgramId(String eisProgramId) {
		this.eisProgramId = eisProgramId;
	}
	public BigDecimal getCoordinateTolerance() {
		return coordinateTolerance;
	}
	public void setCoordinateTolerance(BigDecimal coordinateTolerance) {
		this.coordinateTolerance = coordinateTolerance;
	}
	
}
