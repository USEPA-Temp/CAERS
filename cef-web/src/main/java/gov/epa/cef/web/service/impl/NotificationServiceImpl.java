package gov.epa.cef.web.service.impl;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import gov.epa.cef.web.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
    
    private final String REPORT_SUBMITTED_TO_SLT_SUBJECT = "Emission Report Submitted for {0}";
    private final String REPORT_SUBMITTED_TO_SLT_BODY = "A new emissions report has been submitted for the {0} facility "
            + "for reporting year {1} in the EPA Common Emissions Form.";
    
    private final String REPORT_REJECTED_BY_SLT_SUBJECT = "{0} {1} Emissions Report Rejected";
    private final String REPORT_REJECTED_BY_SLT_BODY_TEMPLATE = "reportRejected";
    
    private final String REPORT_ACCEPTED_BY_SLT_SUBJECT = "{0} {1} Emissions Report Accepted";
    private final String REPORT_ACCEPTED_BY_SLT_BODY_TEMPLATE = "reportAccepted";
  
    @Autowired
    public JavaMailSender emailSender;
    
    //note: Spring and Thymeleaf are "auto-configured" based on the spring-boot-starter-thymeleaf dependency in the pom.xml file
    //Spring/Thymeleaf will automatically assume that template files are located in the resources/templates folder and end in .html
    @Autowired
    private TemplateEngine templateEngine;
 
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
        } catch (MailException e) {
            LOGGER.error("sendSimpleMessage - unable to send email message. - {}", e.getMessage());
        }
    }
    
    public void sendHtmlMessage(String to, String from, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
        };
        try {
        	emailSender.send(messagePreparator);
        } catch (MailException e) {
        	LOGGER.error("sendHTMLMessage - unable to send email message. - {}", e.getMessage());
        }
    }
    
    public void sendReportSubmittedNotification(String to, String from, String facilityName, String reportingYear)
    {
        String emailSubject = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_SUBJECT, facilityName);
        String emailBody = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_BODY, facilityName, reportingYear);
        sendSimpleMessage(to, from, emailSubject, emailBody);
    }
    
    public void sendReportRejectedNotification(String to, String from, String facilityName, String reportingYear, String comments)
    {      
    	String emailSubject = MessageFormat.format(REPORT_REJECTED_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        String emailBody = templateEngine.process(REPORT_REJECTED_BY_SLT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }
    
    public void sendReportAcceptedNotification(String to, String from, String facilityName, String reportingYear, String comments)
    {
    	String emailSubject = MessageFormat.format(REPORT_ACCEPTED_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        String emailBody = templateEngine.process(REPORT_ACCEPTED_BY_SLT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }
}