package gov.epa.cef.web.repository;

import org.springframework.data.repository.CrudRepository; 
import org.springframework.data.repository.query.Param;
import gov.epa.cef.web.domain.UserFeedback;
import java.util.List;


public interface UserFeedbackRepository extends CrudRepository<UserFeedback, Long> {

    /**
     * Retrieve User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    UserFeedback findByReportId(@Param("id") Long id);
    
    /**
     * Retrieve a List of User Feedback for a Report Id
     * @param id
     * @return userFeedback
     */
    List<UserFeedback> findAllByReportId(@Param("id") Long id);
}
