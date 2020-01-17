package gov.epa.cef.web.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.epa.cef.web.domain.FacilitySite;

public class EmissionsReportStarterDto {

    enum SourceType {
        previous, frs, fromScratch
    }

    private String eisProgramId;
    
    private String frsFacilityId;
    
    private String stateCode;

	private SourceType source;

    private Short year;
    
    private FacilitySite facilitySite;

    public String getEisProgramId() {

        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {

        this.eisProgramId = eisProgramId;
    }

    public SourceType getSource() {

        return source;
    }
    
    public String getFrsFacilityId() {
		return frsFacilityId;
	}

	public void setFrsFacilityId(String frsFacilityId) {
		this.frsFacilityId = frsFacilityId;
	}

    public void setSource(SourceType source) {

        this.source = source;
    }

    public Short getYear() {

        return year;
    }

    public void setYear(Short year) {

        this.year = year;
    }

    @JsonIgnore
    public boolean isSourceFrs() {

        return this.source != null && this.source.equals(SourceType.frs);
    }
    
    public boolean isSourceNew() {
    	return this.source != null && this.source.equals(SourceType.fromScratch);
    }

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public FacilitySite getFacilitySite() {
		return facilitySite;
	}

	public void setFacilitySite(FacilitySite facilitySite) {
		this.facilitySite = facilitySite;
	}
}