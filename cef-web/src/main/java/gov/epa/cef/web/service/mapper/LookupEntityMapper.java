package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import gov.epa.cef.web.domain.CalculationMethodCode;
import gov.epa.cef.web.domain.EmissionsOperatingTypeCode;
import gov.epa.cef.web.domain.FacilityCategoryCode;
import gov.epa.cef.web.domain.FipsStateCode;
import gov.epa.cef.web.domain.NaicsCode;
import gov.epa.cef.web.domain.OperatingStatusCode;
import gov.epa.cef.web.domain.Pollutant;
import gov.epa.cef.web.domain.ReportingPeriodCode;
import gov.epa.cef.web.domain.UnitMeasureCode;
import gov.epa.cef.web.domain.common.BaseLookupEntity;
import gov.epa.cef.web.repository.LookupRepositories;
import gov.epa.cef.web.service.dto.CalculationMethodCodeDto;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilityCategoryCodeDto;
import gov.epa.cef.web.service.dto.FipsStateCodeDto;
import gov.epa.cef.web.service.dto.PollutantDto;
import gov.epa.cef.web.service.dto.UnitMeasureCodeDto;


@Mapper(componentModel = "spring", uses = {})
public abstract class LookupEntityMapper {

    @Autowired
    private LookupRepositories repos;

    public abstract CodeLookupDto toDto(BaseLookupEntity source);

    public abstract List<CodeLookupDto> toDtoList(List<BaseLookupEntity> source);

    public abstract CodeLookupDto naicsCodeToDto(NaicsCode code);

    public abstract CodeLookupDto reportingPeriodCodeToDto(ReportingPeriodCode source);
    
    public abstract CodeLookupDto emissionsOperatingTypeCodeToDto(EmissionsOperatingTypeCode source);

    public abstract CalculationMethodCodeDto calculationMethodCodeToDto(CalculationMethodCode source);

    public abstract FacilityCategoryCodeDto facilityCategoryCodeToDto(FacilityCategoryCode code);

    public abstract UnitMeasureCodeDto unitMeasureCodeToDto(UnitMeasureCode source);

    public abstract PollutantDto pollutantToDto(Pollutant source);
    
    public abstract FipsStateCodeDto fipsStateCodeToDto(FipsStateCode source);

    @Named("CalculationMethodCode")
    public CalculationMethodCode dtoToCalculationMethodCode(CodeLookupDto source) {
        if (source != null) {
            return repos.methodCodeRepo().findById(source.getCode()).orElse(null);
        }
        return null;
    }

    @Named("OperatingStatusCode")
    public OperatingStatusCode dtoToOperatingStatusCode(CodeLookupDto source) {
        if (source != null) {
            return repos.operatingStatusRepo().findById(source.getCode()).orElse(null);
        }
        return null;
    }

    public Pollutant pollutantDtoToPollutant(PollutantDto source) {
        if (source != null) {
            return repos.pollutantRepo().findById(source.getPollutantCode()).orElse(null);
        }
        return null;
    }

    @Named("UnitMeasureCode")
    public UnitMeasureCode dtoToUnitMeasureCode(CodeLookupDto source) {
        if (source != null) {
            return repos.uomRepo().findById(source.getCode()).orElse(null);
        }
        return null;
    }
    
    @Named("FipsStateCode")
    public FipsStateCode dtoToFipsStateCode(FipsStateCodeDto source) {
        if (source != null) {
            return repos.stateCodeRepo().findById(source.getCode()).orElse(null);
        }
        return null;
    }
}