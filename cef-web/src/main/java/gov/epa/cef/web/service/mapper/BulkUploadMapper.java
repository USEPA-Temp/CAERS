package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.Emission;
import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.OperatingDetail;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.ReportingPeriod;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.OperatingDetailBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReportingPeriodBulkUploadDto;

@Mapper(componentModel = "spring", uses = {LookupEntityMapper.class})
public interface BulkUploadMapper {

    @Mapping(target = "facilitySites", ignore = true)
    @Mapping(target = "emissionsUnits", ignore = true)
    @Mapping(target = "emissionsProcesses", ignore = true)
    @Mapping(target = "releasePoints", ignore = true)
    @Mapping(target = "releasePointAppts", ignore = true)
    @Mapping(target = "reportingPeriods", ignore = true)
    @Mapping(target = "emissions", ignore = true)
    EmissionsReportBulkUploadDto emissionsReportToDto(EmissionsReport source);

    @Mapping(source="facilityCategoryCode.code", target="facilityCategoryCode")
    @Mapping(source="facilitySourceTypeCode.code", target="facilitySourceTypeCode")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    @Mapping(source="tribalCode.code", target="tribalCode")
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
    ReportingPeriod reportingPeriodFromDto(ReportingPeriodBulkUploadDto source);

    @Mapping(source="emissionsProcess.id", target="emissionsProcessId")
    @Mapping(source="reportingPeriodTypeCode.code", target="reportingPeriodTypeCode")
    @Mapping(source="emissionsOperatingTypeCode.code", target="emissionsOperatingTypeCode")
    @Mapping(source="calculationParameterTypeCode.code", target="calculationParameterTypeCode")
    @Mapping(source="calculationParameterUom.code", target="calculationParameterUom")
    @Mapping(source="calculationMaterialCode.code", target="calculationMaterialCode")
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

    @Mapping(source="facilitySite.id", target="facilitySiteId")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    @Mapping(source="typeCode.code", target="typeCode")
    @Mapping(source="stackHeightUomCode.code", target="stackHeightUomCode")
    @Mapping(source="stackDiameterUomCode.code", target="stackDiameterUomCode")
    @Mapping(source="exitGasVelocityUomCode.code", target="exitGasVelocityUomCode")
    @Mapping(source="exitGasFlowUomCode.code", target="exitGasFlowUomCode")
    @Mapping(source="operatingStatusCode.code", target="operatingStatusCode")
    ReleasePointBulkUploadDto releasePointToDto(ReleasePoint source);

    List<ReleasePointBulkUploadDto> releasePointToDtoList(List<ReleasePoint> source);

}
