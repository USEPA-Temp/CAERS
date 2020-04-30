package gov.epa.cef.web.service;

public interface NotificationService {
   
    public void sendReportSubmittedNotification(String to, String from, String facilityName, String reportingYear);
    public void sendReportRejectedNotification(String to, String from, String facilityName, String reportingYear, String comments);
    public void sendReportAcceptedNotification(String to, String from, String facilityName, String reportingYear, String comments);
    public void sendSccUpdateFailedNotification(Exception exception);
}
