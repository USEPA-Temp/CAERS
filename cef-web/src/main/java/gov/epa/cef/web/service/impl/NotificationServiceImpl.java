/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.domain.Communication;
import gov.epa.cef.web.domain.Attachment;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.repository.AttachmentRepository;
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

import javax.mail.internet.InternetAddress;

@Service
public class NotificationServiceImpl implements NotificationService {

    Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);


    private final String REPORT_SUBMITTED_BY_CERT_SUBJECT = "Submitted - {0} Emissions Report for {1}";
    private final String REPORT_SUBMITTED_BY_CERT_BODY_TEMPLATE = "reportSubmitted";
    
    private final String REPORT_REJECTED_BY_SLT_SUBJECT = "Returned - {0} Emissions Report for {1}";
    private final String REPORT_REJECTED_BY_SLT_BODY_TEMPLATE = "reportRejected";

    private final String REPORT_ACCEPTED_BY_SLT_SUBJECT = "Accepted - {0} Emissions Report for {1}";
    private final String REPORT_ACCEPTED_BY_SLT_BODY_TEMPLATE = "reportAccepted";
    
    private final String REPORT_BEGIN_ADVANCED_QA_BY_SLT_SUBJECT = "In Review - {0} Emissions Report for {1}";
    private final String REPORT_BEGIN_ADVANCED_QA_BY_SLT_BODY_TEMPLATE = "reportAdvancedQA";

    private final String SCC_UPDATE_FAILED_SUBJECT = "SCC Update Task Failed";
    private final String SCC_UPDATE_FAILED_BODY_TEMPLATE = "sccUpdateFailed";

    private final String USER_ACCESS_REQUEST_SUBJECT = "User {0} has requested access to facility {1}";
    private final String USER_ACCESS_REQUEST_BODY_TEMPLATE = "userAccessRequest";

    private final String USER_ASSOCIATION_ACCEPTED_SUBJECT = "Your request to access the {0} facility in the Combined Air Emissions Reporting System has been approved";
    private final String USER_ASSOCIATION_ACCEPTED_BODY_TEMPLATE = "userAssociationAccepted";

    private final String USER_ASSOCIATION_REJECTED_SUBJECT = "Your request to access the {0} facility in the Combined Air Emissions Reporting System has been rejected";
    private final String USER_ASSOCIATION_REJECTED_BODY_TEMPLATE = "userAssociationRejected";

    private final String USER_FEEDBACK_SUBMITTED_SUBJECT = "User feedback Submitted for {0} {1}";
    private final String USER_FEEDBACK_SUBMITTED_BODY_TEMPLATE = "userFeedback";
    
    private final String SLT_NOTIFICATION_GENERIC_BODY_TEMPLATE = "sltNotification";

    @Autowired
    public JavaMailSender emailSender;

    //note: Spring and Thymeleaf are "auto-configured" based on the spring-boot-starter-thymeleaf dependency in the pom.xml file
    //Spring/Thymeleaf will automatically assume that template files are located in the resources/templates folder and end in .html
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private AdminPropertyProvider propertyProvider;
    
    @Autowired
    private AttachmentRepository attachmentsRepo;

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

    public void sendHtmlMessage(String to, String cc, String from, String subject, String body) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            if (cc != null) {
            	messageHelper.setCc(cc);
            }
        };
        try {
        	emailSender.send(messagePreparator);
        } catch (MailException e) {
        	logger.error("sendHTMLMessage - unable to send email message. - {}", e.getMessage());
        }
    }
    
    public void sendMassHtmlMessage(String bcc, String cc, String from, String subject, String body) {
    	MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom(from);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, true);
            if (cc != null) {
            	messageHelper.setCc(cc);
            }
            if (bcc != null) {
            	messageHelper.setBcc(InternetAddress.parse(bcc));
            }
        };
        try {
        	emailSender.send(messagePreparator);
        } catch (MailException e) {
        	logger.error("sendBulkHTMLMessage - unable to send email message. - {}", e.getMessage());
        }
    }
    
    public void sendHtmlMessage(String to, String from, String subject, String body) {
    	sendHtmlMessage(to, null, from, subject, body);
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
    
    public void sendReportSubmittedNotification(String to, String cc, String from, String facilityName, String reportingYear, String slt, String sltEmail, String cdxSubmissionUrl)
    {
    	String emailSubject = MessageFormat.format(REPORT_SUBMITTED_BY_CERT_SUBJECT, reportingYear, facilityName);
    	Context context = new Context();
    	context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("sltEmail", sltEmail);
        context.setVariable("slt", slt);
        context.setVariable("cdxSubmissionUrl", cdxSubmissionUrl);
        
        String emailBody = templateEngine.process(REPORT_SUBMITTED_BY_CERT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, cc, from, emailSubject, emailBody);
    }
    
    public void sendReportRejectedNotification(String to, String cc, String from, String facilityName, String reportingYear, String comments, Long attachmentId, String slt, String sltEmail)
    {
        String emailSubject = MessageFormat.format(REPORT_REJECTED_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        context.setVariable("sltEmail", sltEmail);
        context.setVariable("slt", slt);
        
        if (attachmentId != null) {
            Attachment attachment = attachmentsRepo.findById(attachmentId)
                    .orElseThrow(() -> new NotExistException("Report Attachment", attachmentId));
            
            context.setVariable("attachment", attachment.getFileName());
        }
        
        String emailBody = templateEngine.process(REPORT_REJECTED_BY_SLT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, cc, from, emailSubject, emailBody);
    }

    public void sendReportAcceptedNotification(String to, String from, String facilityName, String reportingYear, String comments, String slt, String sltEmail)
    {
        String emailSubject = MessageFormat.format(REPORT_ACCEPTED_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        context.setVariable("sltEmail", sltEmail);
        context.setVariable("slt", slt);
        
        String emailBody = templateEngine.process(REPORT_ACCEPTED_BY_SLT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }
    
    public void sendReportAdvancedQANotification(String to, String from, String facilityName, String reportingYear, String slt, String sltEmail)
    {
        String emailSubject = MessageFormat.format(REPORT_BEGIN_ADVANCED_QA_BY_SLT_SUBJECT, reportingYear, facilityName);
        Context context = new Context();
        context.setVariable("reportingYear", reportingYear);
        context.setVariable("facilityName", facilityName);
        context.setVariable("sltEmail", sltEmail);
        context.setVariable("slt", slt);
        
        String emailBody = templateEngine.process(REPORT_BEGIN_ADVANCED_QA_BY_SLT_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }

    public void sendSccUpdateFailedNotification(Exception exception) {
        String emailSubject = SCC_UPDATE_FAILED_SUBJECT;
        Context context = new Context();
        context.setVariable("exception", exception);
        String emailBody = templateEngine.process(SCC_UPDATE_FAILED_BODY_TEMPLATE, context);
        sendAdminEmail(emailSubject, emailBody);
    }

    public void sendUserAccessRequestNotification(String to, String from, String facilityName, String agencyFacilityId, String userName, String userEmail, String role)
    {
        String emailSubject = MessageFormat.format(USER_ACCESS_REQUEST_SUBJECT, userName, facilityName);
        Context context = new Context();
        context.setVariable("userName", userName);
        context.setVariable("userEmail", userEmail);
        context.setVariable("role", role);
        context.setVariable("facilityName", facilityName);
        context.setVariable("agencyFacilityId", agencyFacilityId);
        String emailBody = templateEngine.process(USER_ACCESS_REQUEST_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }

    public void sendUserAssociationAcceptedNotification(String to, String from, String facilityName, String role)
    {
        String emailSubject = MessageFormat.format(USER_ASSOCIATION_ACCEPTED_SUBJECT, facilityName);
        Context context = new Context();
        context.setVariable("role", role);
        context.setVariable("facilityName", facilityName);
        String emailBody = templateEngine.process(USER_ASSOCIATION_ACCEPTED_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
    }
    
    public void sendSLTNotification(String cc, String from, String sltEmail, Communication communication)
    {
        String emailSubject = communication.getSubject();
        Context context = new Context();
        context.setVariable("content", communication.getContent());
        context.setVariable("sltEmail", sltEmail);
        context.setVariable("slt", communication.getProgramSystemCode().getCode());
        context.setVariable("sltDescription", communication.getProgramSystemCode().getDescription());
        
        if (communication.getAttachments() != null) {
            Attachment attachment = attachmentsRepo.findById(communication.getAttachments().getId())
                    .orElseThrow(() -> new NotExistException("Communication Attachment", communication.getAttachments().getId()));
            
            context.setVariable("attachment", attachment.getFileName());
        }
        
        String emailBody = templateEngine.process(SLT_NOTIFICATION_GENERIC_BODY_TEMPLATE, context);
        sendMassHtmlMessage(communication.getRecipientEmail(), cc, from, emailSubject, emailBody);
    }

    public void sendUserAssociationRejectedNotification(String to, String from, String facilityName, String role, String comments)
    {
        String emailSubject = MessageFormat.format(USER_ASSOCIATION_REJECTED_SUBJECT, facilityName);
        Context context = new Context();
        context.setVariable("role", role);
        context.setVariable("facilityName", facilityName);
        context.setVariable("comments", comments);
        String emailBody = templateEngine.process(USER_ASSOCIATION_REJECTED_BODY_TEMPLATE, context);
        sendHtmlMessage(to, from, emailSubject, emailBody);
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
