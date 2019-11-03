package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsProcessBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsReportBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.EmissionsUnitBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.FacilitySiteBulkUploadDto;
import gov.epa.cef.web.service.dto.bulkUpload.ReleasePointBulkUploadDto;

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
