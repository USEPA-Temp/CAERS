package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.domain.ReportAttachment;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.provider.system.PropertyProvider;
import gov.epa.cef.web.repository.ReportAttachmentRepository;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.dto.UserFeedbackDto;

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

import java.text.MessageFormat;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    private final String REPORT_SUBMITTED_TO_SLT_SUBJECT = "Emission Report Submitted for {0}";
    private final String REPORT_SUBMITTED_TO_SLT_BODY = "A new emissions report has been submitted for the {0} facility "
            + "for reporting year {1} in the EPA Common Emissions Form.";

    private final String REPORT_REJECTED_BY_SLT_SUBJECT = "{0} {1} Emissions Report has been Returned";
    private final String REPORT_REJECTED_BY_SLT_BODY_TEMPLATE = "reportRejected";

    private final String REPORT_ACCEPTED_BY_SLT_SUBJECT = "{0} {1} Emissions Report has been Accepted";
    private final String REPORT_ACCEPTED_BY_SLT_BODY_TEMPLATE = "reportAccepted";

    private final String SCC_UPDATE_FAILED_SUBJECT = "SCC Update Task Failed";
    private final String SCC_UPDATE_FAILED_BODY_TEMPLATE = "sccUpdateFailed";
    
    private final String USER_FEEDBACK_SUBMITTED_SUBJECT = "User feedback Submitted for {0} {1}";
    private final String USER_FEEDBACK_SUBMITTED_BODY_TEMPLATE = "userFeedback";

    @Autowired
    public JavaMailSender emailSender;

    //note: Spring and Thymeleaf are "auto-configured" based on the spring-boot-starter-thymeleaf dependency in the pom.xml file
    //Spring/Thymeleaf will automatically assume that template files are located in the resources/templates folder and end in .html
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private PropertyProvider propertyProvider;
    
    @Autowired
    private ReportAttachmentRepository reportAttachmentsRepo;

    /**
     * Utility method to send a simple email message in plain text.
     *
     * @param to The recipient of the email
     * @param from The sender of the email
     * @param subject The subject of the email
     * @param body text of the email
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
            logger.error("sendSimpleMessage - unable to send email message. - {}", e.getMessage());
        }
    }

    public void sendAdminNotification(AdminEmailType type, Map<String, Object> variables) {

        Context context = new Context();
        context.setVariables(variables);
        String emailBody = this.templateEngine.process(type.template(), context);

        sendAdminEmail(type.subject(), emailBody);
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
        	logger.error("sendHTMLMessage - unable to send email message. - {}", e.getMessage());
        }
    }

    private void sendAdminEmail(String from, String subject, String body) {
        if (this.propertyProvider.getBoolean(AppPropertyName.AdminEmailEnabled)) {
            this.propertyProvider.getStringList(AppPropertyName.AdminEmailAddresses).forEach(email -> {
                sendHtmlMessage(email, from, subject, body);
            });
        } else {
            logger.info("Admin email not sent because Admin emails are disabled.");
        }
    }

    private void sendAdminEmail(String subject, String body) {
        sendAdminEmail(this.propertyProvider.getString(AppPropertyName.DefaultEmailAddress), subject, body);
    }
    
    public void sendReportSubmittedNotification(String to, String from, String facilityName, String reportingYear)
    {
        String emailSubject = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_SUBJECT, facilityName);
        String emailBody = MessageFormat.format(REPORT_SUBMITTED_TO_SLT_BODY, facilityName, reportingYear);
        sendSimpleMessage(to, from, emailSubject, emailBody);
    }

    public void sendReportRejectedNotification(String to, String from, String facilityName, String reportingYear, String comments, Long attachmentId)
    {
    	String emailSubject = MessageFormat.format(REPORT_REJECTED_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        
    	if (attachmentId != null) {
			ReportAttachment attachment = reportAttachmentsRepo.findById(attachmentId)
					.orElseThrow(() -> new NotExistException("Report Attachment", attachmentId));
			
			context.setVariable("attachment", attachment.getFileName());
		}
    	
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

    public void sendSccUpdateFailedNotification(Exception exception) {
        String emailSubject = SCC_UPDATE_FAILED_SUBJECT;
        Context context = new Context();
        context.setVariable("exception", exception);
        String emailBody = templateEngine.process(SCC_UPDATE_FAILED_BODY_TEMPLATE, context);
        sendAdminEmail(emailSubject, emailBody);
    }
    
    public void sendUserFeedbackNotification(UserFeedbackDto userFeedback){
    	
    	String emailSubject = MessageFormat.format(USER_FEEDBACK_SUBMITTED_SUBJECT, userFeedback.getYear().toString(), userFeedback.getFacilityName());
    	Context context = new Context();
    	context.setVariable("facilityName", userFeedback.getFacilityName());
    	context.setVariable("reportingYear", userFeedback.getYear());
    	context.setVariable("userName", userFeedback.getUserName());
    	context.setVariable("userRole", userFeedback.getUserRole());
    	context.setVariable("userId", userFeedback.getUserId());
    	context.setVariable("intuitiveRating", userFeedback.getIntuitiveRating());
    	context.setVariable("dataEntryScreens", userFeedback.getDataEntryScreens());
    	context.setVariable("dataEntryBulkUpload", userFeedback.getDataEntryBulkUpload());
    	context.setVariable("calculationScreens", userFeedback.getCalculationScreens());
    	context.setVariable("controlsAndControlPathAssignments", userFeedback.getControlsAndControlPathAssignments());
    	context.setVariable("qualityAssurance", userFeedback.getQualityAssuranceChecks());
    	context.setVariable("overallReportingTime", userFeedback.getOverallReportingTime());
    	context.setVariable("openQuestion1", userFeedback.getBeneficialFunctionalityComments());
    	context.setVariable("openQuestion2", userFeedback.getDifficultFunctionalityComments());
    	context.setVariable("openQuestion3", userFeedback.getEnhancementComments());

    	String emailBody = templateEngine.process(USER_FEEDBACK_SUBMITTED_BODY_TEMPLATE, context);
    	sendAdminEmail(emailSubject, emailBody);
    }

}
