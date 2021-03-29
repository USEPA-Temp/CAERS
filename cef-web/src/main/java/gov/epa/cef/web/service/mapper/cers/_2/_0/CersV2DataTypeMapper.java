package gov.epa.cef.web.service.mapper.cers._2._0;

import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsProcess;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.ReleasePoint;
import net.exchangenetwork.schema.cer._2._0.CERSDataType;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    uses = {CersV2FacilitySiteMapper.class})
public interface CersV2DataTypeMapper {

    @Mapping(source="year", target="emissionsYear")
    @Mapping(source="facilitySites", target="facilitySite")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    CERSDataType fromEmissionsReport(EmissionsReport source);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="operatingStatusCode", target="operatingStatusCode")
    @Mapping(source="facilitySite", target="facilitySite")
    @Mapping(source="unitIdentifier", target="unitIdentifier")
    @Mapping(source="statusYear", target="statusYear")
    EmissionsUnit emissionsUnitToNonOperatingEmissionsUnit(EmissionsUnit source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="operatingStatusCode", target="operatingStatusCode")
    @Mapping(source="emissionsUnit", target="emissionsUnit")
    @Mapping(source="emissionsProcessIdentifier", target="emissionsProcessIdentifier")
    @Mapping(source="statusYear", target="statusYear")
    EmissionsProcess processToNonOperatingEmissionsProcess(EmissionsProcess source);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="facilitySite", target="facilitySite")
    @Mapping(source="operatingStatusCode", target="operatingStatusCode")
    @Mapping(source="statusYear", target="statusYear")
    @Mapping(source="releasePointIdentifier", target="releasePointIdentifier")
    ReleasePoint releasePointToNonOperatingReleasePoint(ReleasePoint source);
}
