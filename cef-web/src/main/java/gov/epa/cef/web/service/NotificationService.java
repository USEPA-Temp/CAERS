package gov.epa.cef.web.service;

public interface NotificationService {
   
    public void sentReportSubmittedNotification(String to, String from, String facilityName, String reportingYear);
}
