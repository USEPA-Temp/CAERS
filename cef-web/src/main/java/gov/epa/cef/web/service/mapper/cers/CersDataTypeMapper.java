package gov.epa.cef.web.service.mapper.cers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.FacilitySite;
import net.exchangenetwork.schema.cer._1._2.CERSDataType;

@Mapper(componentModel = "spring", uses = {})
public interface CersDataTypeMapper {

    @Mapping(source="emissionsReport.year", target="emissionsYear")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    CERSDataType fromFacilitySite(FacilitySite source);
}
