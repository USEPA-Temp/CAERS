package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.service.dto.PropertyDto;

@RestController
@RequestMapping("/api/property")
public class PropertyApi {

    @Autowired
    private AdminPropertyProvider propertyProvider;
    
    @Autowired
    private CefConfig cefConfig;

    /**
     * Retrieve announcement enabled property
     * @return
     */
    @GetMapping(value = "/announcement/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveAnnouncementEnabled() {
        Boolean result = propertyProvider.getBoolean(AppPropertyName.FeatureAnnouncementEnabled);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve announcement text property
     * @return
     */
    @GetMapping(value = "/announcement/text")
    @ResponseBody
    public ResponseEntity<PropertyDto> retrieveAnnouncementText() {
        String result = propertyProvider.getString(AppPropertyName.FeatureAnnouncementText);
        return new ResponseEntity<>(new PropertyDto().withValue(result), HttpStatus.OK);
    }

    /**
     * Retrieve bulk entry enabled property
     * @return
     */
    @GetMapping(value = "/bulkEntry/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveBulkEntryEnabled() {
        Boolean result = propertyProvider.getBoolean(AppPropertyName.FeatureBulkEntryEnabled);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve user feedback enabled property
     * @return
     */
    @GetMapping(value = "/userFeedback/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveUserFeedbackEnabled() {
        Boolean result = propertyProvider.getBoolean(AppPropertyName.FeatureUserFeedbackEnabled);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve multipartfile maximum file upload size
     * @return
     */
    @GetMapping(value = "/attachments/maxSize")
    @ResponseBody
    public ResponseEntity<PropertyDto> retrieveReportAttachmentMaxSize() {
    	Long result = Long.valueOf(this.cefConfig.getMaxFileSize().replaceAll("[^0-9]", ""));
        return new ResponseEntity<>(new PropertyDto().withValue(result.toString()), HttpStatus.OK);
    }

    /**
     * Retrieve excel export enabled property
     * @return
     */
    @GetMapping(value = "/excelExport/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveExcelExportEnabled() {
        Boolean result = this.cefConfig.getExcelExportEnabled();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
