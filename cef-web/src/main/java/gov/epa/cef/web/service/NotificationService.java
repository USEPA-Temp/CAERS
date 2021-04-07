package gov.epa.cef.web.service;

import java.util.Map;

import gov.epa.cef.web.service.dto.UserFeedbackDto;

public interface NotificationService {

    void sendReportSubmittedNotification(String to, String cc, String from, String facilityName, String reportingYear, String slt, String sltEmail, String submissionId);
    void sendReportRejectedNotification(String to, String cc, String from, String facilityName, String reportingYear, String comments, Long attachmentId, String slt, String sltEmail);
    void sendReportAcceptedNotification(String to, String from, String facilityName, String reportingYear, String comments, String slt, String sltEmail);
    void sendSccUpdateFailedNotification(Exception exception);
    void sendUserAccessRequestNotification(String to, String from, String facilityName, String agencyFacilityId, String userName, String userEmail, String role);
    void sendUserAssociationAcceptedNotification(String to, String from, String facilityName, String role);
    void sendUserAssociationRejectedNotification(String to, String from, String facilityName, String role, String comments);
    void sendUserFeedbackNotification(UserFeedbackDto userFeedback);
    
    void sendAdminNotification(AdminEmailType type, Map<String, Object> context);

    enum AdminEmailType {

        AdminTest("CAER CEF Admin Email Test", "adminTest"),
        VirusScanFailure("Virus Scanner Web Service Failed", "virusScanFailed");

        private final String subject;
        private final String template;

        AdminEmailType(String subject, String template) {

            this.subject = subject;
            this.template = template;
        }

        public String template() {
            return this.template;
        }

        public String subject() {
            return this.subject;
        }
    }
    
}
