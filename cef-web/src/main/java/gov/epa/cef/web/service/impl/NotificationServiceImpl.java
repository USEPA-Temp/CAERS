package gov.epa.cef.web.service.impl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    private final String REPORT_SUBMITTED_TO_SLT_SUBJECT = "Emission Report Submitted for {0}";
    private final String REPORT_SUBMITTED_TO_SLT_BODY = "A new emissions report has been submitted for the {0} facility "
            + "for reporting year {1} in the EPA Common Emissions Form.";
  
    @Autowired
    public JavaMailSender emailSender;
 
    /**
     * Utility method to send a simple email message in plain text. 
     * 
     * @param to The recipient of the email
     * @param from The sender of the email
     * @param subject The subject of the email
     * @param the body text of the email
     */
    private void sendSimpleMessage(String to, String from, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage(); 
            message.setFrom(from);
            message.setTo(to); 
            message.setSubject(subject); 
            message.setText(body);
            emailSender.send(message);
        } catch (Exception e) {
            LOGGER.error("sendSimpleMessage - unable to send email message. - " + e.getStackTrace().toString());
        }
    }
    
    public void sentReportSubmittedNotification(String to, String from, String facilityName, String reportingYear)
    {
        String emailSubject = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_SUBJECT, facilityName);
        String emailBody = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_BODY, facilityName, reportingYear);
        sendSimpleMessage(to, from, emailSubject, emailBody);
    }
}