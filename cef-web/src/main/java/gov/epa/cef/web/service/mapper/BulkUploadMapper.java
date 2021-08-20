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
package gov.epa.cef.web.service.mapper;

import gov.epa.cef.web.domain.Control;
import gov.epa.cef.web.domain.ControlAssignment;
import gov.epa.cef.web.domain.ControlPath;
import gov.epa.cef.web.domain.ControlPathPollutant;
import gov.epa.cef.web.domain.ControlPollutant;
import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionFormulaVariable;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilityNAICSXref;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.FacilitySiteContact;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReleasePointAppt;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.bulkUpload.ControlAssignmentBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPathPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ControlPollutantBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionFormulaVariableBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilityNAICSBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteContactBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointApptBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReportingPeriodBulkUploadDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {LookupEntityMapper.class})
public interface BulkUploadMapper {

    @Mapping(target = "facilitySites", ignore = true)
    @Mapping(target = "emissionsUnits", ignore = true)
    @Mapping(target = "emissionsProcesses", ignore = true)
    @Mapping(target = "releasePoints", ignore = true)
    @Mapping(target = "releasePointAppts", ignore = true)
    @Mapping(target = "reportingPeriods", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    @Mapping(source="masterFacilityRecord.id", target="masterFacilityRecordId")
    EmissionsReportBulkUploadDto emissionsReportToDto(EmissionsReport source);

    @Mapping(source="countyCode.countyCode", target="countyCode")
    @Mapping(source="countyCode.name", target="county")
    @Mapping(source="stateCode.uspsCode", target="stateCode")
    @Mapping(source="stateCode.code", target="stateFipsCode")
    @Mapping(source="mailingStateCode.uspsCode", target="mailingStateCode")
    @Mapping(source="facilityCategoryCode.code", target="facilityCategoryCode")
    @Mapping(source="facilitySourceTypeCode.code", target="facilitySourceTypeCode")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    @Mapping(source="tribalCode.code", target="tribalCode")
    @Mapping(source="emissionsReport.masterFacilityRecord.eisProgramId", target="eisProgramId")
    @Mapping(source="emissionsReport.masterFacilityRecord.id", target="masterFacilityRecordId")
    FacilitySiteBulkUploadDto facilitySiteToDto(FacilitySite source);

    List<FacilitySiteBulkUploadDto> facilitySiteToDtoList(List<FacilitySite> source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="unitTypeCode.code", target="typeCode")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCodeDescription")
    @Mapping(source="unitOfMeasureCode.code", target="unitOfMeasureCode")
    EmissionsUnitBulkUploadDto emissionsUnitToDto(EmissionsUnit source);

    List<EmissionsUnitBulkUploadDto> emissionsUnitToDtoList(List<EmissionsUnit> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aircraftEngineTypeCode", ignore = true)
    @Mapping(target = "operatingStatusCode", ignore = true)
    EmissionsProcess emissionsProcessFromDto(EmissionsProcessBulkUploadDto source);

    @Mapping(source="emissionsUnit.id", target="emissionsUnitId")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    @Mapping(source="aircraftEngineTypeCode.code", target="aircraftEngineTypeCode")
    EmissionsProcessBulkUploadDto emissionsProcessToDto(EmissionsProcess source);

    List<EmissionsProcessBulkUploadDto> emissionsProcessToDtoList(List<EmissionsProcess> source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target="reportingPeriodTypeCode", ignore = true)
    @Mapping(target="emissionsOperatingTypeCode", ignore = true)
    @Mapping(target="calculationParameterTypeCode", ignore = true)
    @Mapping(target="calculationParameterUom", ignore = true)
    @Mapping(target="calculationMaterialCode", ignore = true)
    @Mapping(target="fuelUseUom", ignore = true)
    @Mapping(target="fuelUseMaterialCode", ignore = true)
    @Mapping(target="heatContentUom", ignore = true)
    ReportingPeriod reportingPeriodFromDto(ReportingPeriodBulkUploadDto source);

    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    @Mapping(source="reportingPeriodTypeCode.code", target="reportingPeriodTypeCode")
    @Mapping(source="emissionsOperatingTypeCode.code", target="emissionsOperatingTypeCode")
    @Mapping(source="calculationParameterTypeCode.code", target="calculationParameterTypeCode")
    @Mapping(source="calculationParameterUom.code", target="calculationParameterUom")
    @Mapping(source="calculationMaterialCode.code", target="calculationMaterialCode")
    @Mapping(source="fuelUseUom.code", target="fuelUseUom")
    @Mapping(source="fuelUseMaterialCode.code", target="fuelUseMaterialCode")
    @Mapping(source="heatContentUom.code", target="heatContentUom")
    ReportingPeriodBulkUploadDto reportingPeriodToDto(ReportingPeriod source);

    List<ReportingPeriodBulkUploadDto> reportingPeriodToDtoList(List<ReportingPeriod> source);

    @Mapping(target="id", ignore = true)
    @Mapping(source="averageHoursPerDay", target="avgHoursPerDay")
    @Mapping(source="averageDaysPerWeek", target="avgDaysPerWeek")
    @Mapping(source="averageWeeksPerPeriod", target="avgWeeksPerPeriod")
    OperatingDetail operatingDetailFromDto(OperatingDetailBulkUploadDto source);

    @Mapping(source="reportingPeriod.id", target="reportingPeriodId")
    @Mapping(source="avgHoursPerDay", target="averageHoursPerDay")
    @Mapping(source="avgDaysPerWeek", target="averageDaysPerWeek")
    @Mapping(source="avgWeeksPerPeriod", target="averageWeeksPerPeriod")
    OperatingDetailBulkUploadDto operatingDetailToDto(OperatingDetail source);

    List<OperatingDetailBulkUploadDto> operatingDetailToDtoList(List<OperatingDetail> source);

    @Mapping(target="id", ignore = true)
    @Mapping(target="pollutant", ignore = true)
    @Mapping(target="emissionsUomCode", ignore = true)
    @Mapping(target="emissionsCalcMethodCode", ignore = true)
    @Mapping(target="emissionsNumeratorUom", ignore = true)
    @Mapping(target="emissionsDenominatorUom", ignore = true)
    Emission emissionsFromDto(EmissionBulkUploadDto source);

    @Mapping(source="reportingPeriod.id", target="reportingPeriodId")
    @Mapping(source="pollutant.pollutantCode", target="pollutantCode")
    @Mapping(source="emissionsUomCode.code", target="emissionsUomCode")
    @Mapping(source="emissionsCalcMethodCode.code", target="emissionsCalcMethodCode")
    @Mapping(source="emissionsNumeratorUom.code", target="emissionsNumeratorUom")
    @Mapping(source="emissionsDenominatorUom.code", target="emissionsDenominatorUom")
    EmissionBulkUploadDto emissionToDto(Emission source);

    List<EmissionBulkUploadDto> emissionToDtoList(List<Emission> source);

    @Mapping(source="emission.id", target="emissionId")
    @Mapping(source="variableCode.code", target="emissionFormulaVariableCode")
    EmissionFormulaVariableBulkUploadDto emissionFormulaVariableToDto(EmissionFormulaVariable source);

    List<EmissionFormulaVariableBulkUploadDto> emissionFormulaVariableToDtoList(List<EmissionFormulaVariable> source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="typeCode.code", target="typeCode")
    @Mapping(source="stackHeightUomCode.code", target="stackHeightUomCode")
    @Mapping(source="stackDiameterUomCode.code", target="stackDiameterUomCode")
    @Mapping(source="stackWidthUomCode.code", target="stackWidthUomCode")
    @Mapping(source="stackLengthUomCode.code", target="stackLengthUomCode")
    @Mapping(source="exitGasVelocityUomCode.code", target="exitGasVelocityUomCode")
    @Mapping(source="exitGasFlowUomCode.code", target="exitGasFlowUomCode")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    @Mapping(source="fugitiveHeightUomCode.code", target="fugitiveHeightUomCode")
    @Mapping(source="fugitiveWidthUomCode.code", target="fugitiveWidthUomCode")
    @Mapping(source="fugitiveLengthUomCode.code", target="fugitiveLengthUomCode")
    @Mapping(source="fenceLineUomCode.code", target="fenceLineUomCode")
    ReleasePointBulkUploadDto releasePointToDto(ReleasePoint source);

    List<ReleasePointBulkUploadDto> releasePointToDtoList(List<ReleasePoint> source);

    @Mapping(target="id", ignore = true)
    @Mapping(target="releasePoint", ignore = true)
    @Mapping(target="emissionsProcess", ignore = true)
    @Mapping(target="controlPath", ignore = true)
    ReleasePointAppt releasePointApptFromDto(ReleasePointApptBulkUploadDto source);

    @Mapping(source="releasePoint.id", target="releasePointId")
    @Mapping(source="emissionsProcess.id", target="emissionProcessId")
    @Mapping(source="controlPath.id", target="controlPathId")
    ReleasePointApptBulkUploadDto releasePointApptToDto(ReleasePointAppt source);

    List<ReleasePointApptBulkUploadDto> releasePointApptToDtoList(List<ReleasePointAppt> source);

    @Mapping(target="id", ignore = true)
    @Mapping(target="facilitySite", ignore = true)
    ControlPath controlPathFromDto(ControlPathBulkUploadDto source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    ControlPathBulkUploadDto controlPathToDto(ControlPath source);

    List<ControlPathBulkUploadDto> controlPathToDtoList(List<ControlPath> source);
    
    @Mapping(target="id", ignore = true)
    @Mapping(target="controlPath", ignore = true)
    @Mapping(target="pollutant", ignore = true)
    ControlPathPollutant controlPathPollutantFromDto(ControlPathPollutantBulkUploadDto source);

    @Mapping(source="controlPath.id", target="controlPathId")
    @Mapping(source="pollutant.pollutantCode", target="pollutantCode")
    ControlPathPollutantBulkUploadDto controlPollutantToDto(ControlPathPollutant source);

    List<ControlPathPollutantBulkUploadDto> controlPathPollutantToDtoList(List<ControlPathPollutant> source);


    @Mapping(target="id", ignore = true)
    @Mapping(target="facilitySite", ignore = true)
    @Mapping(target="operatingStatusCode", ignore = true)
    @Mapping(target="controlMeasureCode", ignore = true)
    Control controlFromDto(ControlBulkUploadDto source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    @Mapping(source="controlMeasureCode.code", target="controlMeasureCode")
    ControlBulkUploadDto controlToDto(Control source);

    List<ControlBulkUploadDto> controlToDtoList(List<Control> source);

    @Mapping(target="id", ignore = true)
    @Mapping(target="control", ignore = true)
    @Mapping(target="controlPath", ignore = true)
    @Mapping(target="controlPathChild", ignore = true)
    ControlAssignment controlAssignmentFromDto(ControlAssignmentBulkUploadDto source);

    @Mapping(source="control.id", target="controlId")
    @Mapping(source="controlPath.id", target="controlPathId")
    @Mapping(source="controlPathChild.id", target="controlPathChildId")
    ControlAssignmentBulkUploadDto controlAssignmentToDto(ControlAssignment source);

    List<ControlAssignmentBulkUploadDto> controlAssignmentToDtoList(List<ControlAssignment> source);

    @Mapping(target="id", ignore = true)
    @Mapping(target="control", ignore = true)
    @Mapping(target="pollutant", ignore = true)
    ControlPollutant controlPollutantFromDto(ControlPollutantBulkUploadDto source);

    @Mapping(source="control.id", target="controlId")
    @Mapping(source="pollutant.pollutantCode", target="pollutantCode")
    ControlPollutantBulkUploadDto controlPollutantToDto(ControlPollutant source);

    List<ControlPollutantBulkUploadDto> controlPollutantToDtoList(List<ControlPollutant> source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="naicsCode.code", target="code")
    FacilityNAICSBulkUploadDto faciliytNAICSToDto(FacilityNAICSXref source);

    List<FacilityNAICSBulkUploadDto> faciliytNAICSToDtoList(List<FacilityNAICSXref> source);

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="countyCode.name", target="county")
    @Mapping(source="countyCode.countyCode", target="countyCode")
    @Mapping(source="type.code", target="type")
    @Mapping(source="stateCode.uspsCode", target="stateCode")
    @Mapping(source="mailingStateCode.uspsCode", target="mailingStateCode")
    FacilitySiteContactBulkUploadDto facilitySiteContactToDto(FacilitySiteContact source);

    List<FacilitySiteContactBulkUploadDto> facilitySiteContactToDtoList(List<FacilitySiteContact> source);

}
