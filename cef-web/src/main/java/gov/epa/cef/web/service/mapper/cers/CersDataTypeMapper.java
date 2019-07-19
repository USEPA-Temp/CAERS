package gov.epa.cef.web.service.mapper.cers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.EmissionsReport;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;

@Mapper(componentModel = "spring", uses = {CersFacilitySiteMapper.class})
public interface CersDataTypeMapper {

    @Mapping(source="year", target="emissionsYear")
    @Mapping(source="facilitySites", target="facilitySite")
    CERSDataType fromEmissionsReport(EmissionsReport source);
}
