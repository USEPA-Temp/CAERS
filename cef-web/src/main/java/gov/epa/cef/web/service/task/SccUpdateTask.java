package gov.epa.cef.web.service.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.service.NotificationService;
import gov.epa.cef.web.service.SccService;

public class SccUpdateTask implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SccService sccService;

    @Autowired 
    private NotificationService notificationService;

    @Autowired
    private AdminPropertyProvider propertyProvider;

    /**
     * Run task to retrieve SCC codes from the webservice that have been updated since this was
     * last run and then update the database with the information from them.
     */
    public void run() {

        if (this.propertyProvider.getBoolean(AppPropertyName.SccUpdateTaskEnabled, false)) {
            try {
                logger.info("SCC Update Task running");
                this.sccService.updatePointSourceSccCodes(this.propertyProvider.getLocalDate(AppPropertyName.LastSccUpdateDate));
                this.propertyProvider.update(AppPropertyName.LastSccUpdateDate, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
                logger.info("SCC Update Task finish");
            } catch (Exception e) {
                logger.error("Exception thrown while updating SCC Codes", e);
                this.notificationService.sendSccUpdateFailedNotification(e);
            }
        }
    }

}
