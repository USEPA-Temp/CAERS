package gov.epa.cef.web.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.google.common.base.MoreObjects;
import gov.epa.cef.web.domain.FacilitySite;

public class EmissionsReportStarterDto {

    public enum SourceType {
        previous, frs, fromScratch
    }

    private String eisProgramId;

    private FacilitySite facilitySite;

    private String frsFacilityId;

    private SourceType source;

    private String stateCode;

    private Short year;

    public String getEisProgramId() {

        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {

        this.eisProgramId = eisProgramId;
    }

    public FacilitySite getFacilitySite() {

        return facilitySite;
    }

    public void setFacilitySite(FacilitySite facilitySite) {

        this.facilitySite = facilitySite;
    }

    public String getFrsFacilityId() {

        return frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {

        this.frsFacilityId = frsFacilityId;
    }

    public SourceType getSource() {

        return source;
    }

    public void setSource(SourceType source) {

        this.source = source;
    }

    public String getStateCode() {

        return stateCode;
    }

    public void setStateCode(String stateCode) {

        this.stateCode = stateCode;
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

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
            .add("eisProgramId", eisProgramId)
            .add("frsFacilityId", frsFacilityId)
            .add("stateCode", stateCode)
            .add("source", source)
            .add("year", year)
            .add("facilitySite", facilitySite)
            .toString();
    }
}
