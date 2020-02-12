package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.ReportDownloadView;
import gov.epa.cef.web.service.dto.ReportDownloadDto;

@Mapper(componentModel = "spring", uses = {})   
public interface ReportDownloadMapper {

    List<ReportDownloadDto> toDtoList(List<ReportDownloadView> reportDownloadViewList);
    
}