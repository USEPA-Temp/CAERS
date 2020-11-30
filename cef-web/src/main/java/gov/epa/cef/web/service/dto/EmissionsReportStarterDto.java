package gov.epa.cef.web.service.dto;

import com.google.common.base.MoreObjects;

public class EmissionsReportStarterDto {

    public enum SourceType {
        previous, frs, fromScratch
    }

    private String eisProgramId;

    private FacilitySiteDto facilitySite;

    private String frsFacilityId;

    private String programSystemCode;

    private SourceType source;

    private String stateFacilityId;

    private Short year;

    public String getStateFacilityId() {

        return stateFacilityId;
    }

    public void setStateFacilityId(String stateFacilityId) {

        this.stateFacilityId = stateFacilityId;
    }

    public String getEisProgramId() {

        return eisProgramId;
    }

    public void setEisProgramId(String eisProgramId) {

        this.eisProgramId = eisProgramId;
    }

    public FacilitySiteDto getFacilitySite() {

        return facilitySite;
    }

    public void setFacilitySite(FacilitySiteDto facilitySite) {

        this.facilitySite = facilitySite;
    }

    public String getFrsFacilityId() {

        return frsFacilityId;
    }

    public void setFrsFacilityId(String frsFacilityId) {

        this.frsFacilityId = frsFacilityId;
    }

    public String getProgramSystemCode() {

        return programSystemCode;
    }

    public void setProgramSystemCode(String programSystemCode) {

        this.programSystemCode = programSystemCode;
    }

    public SourceType getSource() {

        return source;
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

    @Override
    public String toString() {

        return MoreObjects.toStringHelper(this)
            .add("eisProgramId", eisProgramId)
            .add("facilitySite", facilitySite)
            .add("frsFacilityId", frsFacilityId)
            .add("programSystemCode", programSystemCode)
            .add("source", source)
            .add("stateFacilityId", stateFacilityId)
            .add("year", year)
            .toString();
    }
}
