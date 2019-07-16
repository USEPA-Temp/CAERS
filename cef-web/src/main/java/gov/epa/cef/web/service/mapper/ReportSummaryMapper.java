package gov.epa.cef.web.service.mapper;

import org.mapstruct.Mapper;
import java.util.List;
import gov.epa.cef.web.domain.ReportSummary;
import gov.epa.cef.web.service.dto.ReportSummaryDto;

@Mapper(componentModel = "spring", uses = {})
public interface ReportSummaryMapper {

    List<ReportSummaryDto> toDtoList(List<ReportSummary> reportSummaries);

}
