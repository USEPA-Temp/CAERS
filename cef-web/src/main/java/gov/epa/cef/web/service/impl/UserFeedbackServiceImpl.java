package gov.epa.cef.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.repository.UserFeedbackRepository;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.mapper.UserFeedbackMapper;

import java.util.Collections;
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
}
