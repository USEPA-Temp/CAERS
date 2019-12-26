package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.ReportHistory;
import gov.epa.cef.web.service.dto.ReportHistoryDto;

@Mapper(componentModel = "spring", uses = {})
public interface ReportHistoryMapper {
	
	List<ReportHistoryDto> toDtoList(List<ReportHistory> reportHistoryList);
	
}
