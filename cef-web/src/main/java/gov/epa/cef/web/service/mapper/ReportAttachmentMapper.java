package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;

@Mapper(componentModel = "spring", uses = {})
public interface ReportAttachmentMapper {
	
	List<ReportAttachmentDto> toDtoList(List<ReportAttachment> reportAttachmentsList);
	
	@Mapping(source="reportId", target="emissionsReport.id")
	@Mapping(target="attachment", ignore = true)
	ReportAttachment fromDto(ReportAttachmentDto source);
	
	@Mapping(source="emissionsReport.id", target="reportId")
	@Mapping(target="attachment", ignore = true)
	ReportAttachmentDto toDto(ReportAttachment attachment);
	
}
