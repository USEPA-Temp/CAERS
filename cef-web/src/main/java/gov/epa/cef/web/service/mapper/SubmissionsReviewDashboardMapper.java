package gov.epa.cef.web.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import gov.epa.cef.web.domain.SubmissionsReviewDashboardView;
import gov.epa.cef.web.service.dto.SubmissionsReviewDashboardDto;

/**
 * @author ahmahfou
 *
 */
@Mapper(componentModel = "spring", uses = {})   
public interface SubmissionsReviewDashboardMapper {

    List<SubmissionsReviewDashboardDto> toDtoList(List<SubmissionsReviewDashboardView> submissionsReviewDashboardViewList);
    
    
}