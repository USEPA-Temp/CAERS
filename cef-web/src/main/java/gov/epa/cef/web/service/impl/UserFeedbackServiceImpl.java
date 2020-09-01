package gov.epa.cef.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.repository.UserFeedbackRepository;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.dto.IUserFeedbackStatsDto;
import gov.epa.cef.web.service.mapper.UserFeedbackMapper;

import java.util.List;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {
	
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    
    @Autowired
    private UserFeedbackRepository userFeedbackRepo;
    
    @Autowired
    private NotificationService notificationService;
    
    public UserFeedbackDto create(UserFeedbackDto dto) {
		
    	UserFeedback userFeedback = userFeedbackMapper.fromDto(dto);
    	
    	UserFeedbackDto result = userFeedbackMapper.toDto(userFeedbackRepo.save(userFeedback));
    	
        notificationService.sendUserFeedbackNotification(dto);
	  
    	return result;
	}
		
	public void removeReportFromUserFeedback(Long reportId) {
		if(userFeedbackRepo.findAllByReportId(reportId) != null) {
			List<UserFeedback> userFeedback = userFeedbackRepo.findAllByReportId(reportId);
			userFeedback.forEach(submission -> {
				submission.setReportId(null);
				userFeedbackRepo.save(submission);
			});
		}
	}
	
	public List<UserFeedbackDto> retrieveAllByYearAndAgency(Short year, String agency) {
		
		List<UserFeedback> userFeedback = agency.contentEquals("ALL_AGENCIES")
	            ? this.userFeedbackRepo.findAllByYear(year, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "createdDate"))
	            : this.userFeedbackRepo.findByYearAndAgencyCode(year, agency, Sort.by(Sort.DEFAULT_DIRECTION.DESC, "createdDate"));
		
		return userFeedbackMapper.toDtoList(userFeedback);
	}
	
	public IUserFeedbackStatsDto retrieveStatsByYearAndAgency(Short year, String agency) {
		
		IUserFeedbackStatsDto userFeedback = agency.contentEquals("ALL_AGENCIES")
	            ? this.userFeedbackRepo.findAvgByYear(year) 
	            : this.userFeedbackRepo.findAvgByYearAndAgency(year, agency);
	            
		return userFeedback;
	}
	
	public List<String> retrieveAvailableAgencies() {
		
		return this.userFeedbackRepo.findDistinctAgencies(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "agencyCode"));
	}
	
	public List<Short> retrieveAvailableYears() {
		
		return this.userFeedbackRepo.findDistinctYears(Sort.by(Sort.DEFAULT_DIRECTION.DESC, "year"));
	}
	
	
}
