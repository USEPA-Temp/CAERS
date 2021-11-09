/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.dto;

import com.google.common.base.MoreObjects;

import gov.epa.cef.web.domain.ThresholdStatus;

public class EmissionsReportStarterDto {

    public enum SourceType {
        previous, frs, fromScratch
    }

    private String eisProgramId;

    private FacilitySiteDto facilitySite;

    private String frsFacilityId;

    private Long masterFacilityRecordId;
    
    private String programSystemCode;

    private SourceType source;

    private String stateFacilityId;

    private ThresholdStatus thresholdStatus;

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

    public Long getMasterFacilityRecordId() {
        return masterFacilityRecordId;
    }

    public void setMasterFacilityRecordId(Long masterFacilityRecordId) {
        this.masterFacilityRecordId = masterFacilityRecordId;
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

    public ThresholdStatus getThresholdStatus() {
        return thresholdStatus;
    }

    public void setThresholdStatus(ThresholdStatus thresholdStatus) {
        this.thresholdStatus = thresholdStatus;
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
            .add("masterFacilityRecordId", masterFacilityRecordId)
            .add("programSystemCode", programSystemCode)
            .add("source", source)
            .add("stateFacilityId", stateFacilityId)
            .add("year", year)
            .toString();
    }
}
