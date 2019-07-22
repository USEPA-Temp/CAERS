package gov.epa.cef.web.service.mapper.cers;

import java.util.Collections;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReleasePoint;
import net.exchangenetwork.schema.cer._1._2.IdentificationDataType;

@Mapper(componentModel = "spring", uses = {})
public interface CersReleasePointMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(source="releasePointIdentifier", target="identifier")
    @Mapping(source="programSystemCode.code", target="programSystemCode")
    IdentificationDataType identificationFromReleasePoint(ReleasePoint source);

    default List<IdentificationDataType> identificationListFromReleasePoint(ReleasePoint source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(identificationFromReleasePoint(source));
    }
}
