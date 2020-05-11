package gov.epa.cef.web.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.provider.system.PropertyProvider;

@RestController
@RequestMapping("/api/property")
public class PropertyApi {

    @Autowired
    private PropertyProvider propertyProvider;

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

}
