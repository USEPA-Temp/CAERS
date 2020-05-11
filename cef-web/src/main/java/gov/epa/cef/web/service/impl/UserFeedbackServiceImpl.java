package gov.epa.cef.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.ReleasePoint;
import gov.epa.cef.web.domain.UserFeedback;
import gov.epa.cef.web.repository.UserFeedbackRepository;
import gov.epa.cef.web.service.UserFeedbackService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;
import gov.epa.cef.web.service.mapper.UserFeedbackMapper;
import java.util.List;

@Service
public class UserFeedbackServiceImpl implements UserFeedbackService {
	
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    
    @Autowired
    private UserFeedbackRepository userFeedbackRepo;
	
	public UserFeedbackDto create(UserFeedbackDto dto) {
		
    	UserFeedback userFeedback = userFeedbackMapper.fromDto(dto);
    	
    	UserFeedbackDto result = userFeedbackMapper.toDto(userFeedbackRepo.save(userFeedback));
 
    	return result;
	}
	
	public UserFeedbackDto retrieveById(Long reportId) {
		
        UserFeedback userFeedback = userFeedbackRepo.findByReportId(reportId);
        
        return userFeedbackMapper.toDto(userFeedback);
	}
	
	public void setReportIdToNull(Long reportId) {
		if(userFeedbackRepo.findByReportId(reportId) != null){
			UserFeedback userFeedback = userFeedbackRepo.findByReportId(reportId);
			userFeedback.setReportId(null);
			userFeedbackRepo.save(userFeedback);
		}
	}
}
