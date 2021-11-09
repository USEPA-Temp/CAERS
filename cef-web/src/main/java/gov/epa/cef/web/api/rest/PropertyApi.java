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
package gov.epa.cef.web.api.rest;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.epa.cef.web.config.AppPropertyName;
import gov.epa.cef.web.config.CefConfig;
import gov.epa.cef.web.config.SLTPropertyName;
import gov.epa.cef.web.provider.system.AdminPropertyProvider;
import gov.epa.cef.web.provider.system.SLTPropertyProvider;
import gov.epa.cef.web.service.dto.PropertyDto;

@RestController
@RequestMapping("/api/property")
public class PropertyApi {

    @Autowired
    private AdminPropertyProvider propertyProvider;
    
    @Autowired
    private SLTPropertyProvider sltPropertyProvider;
    
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
        Boolean result = propertyProvider.getBoolean(AppPropertyName.FeatureExcelExportEnabled);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve GADNR threshold screening enabled property
     * @return
     */
    @GetMapping(value = "/thresholdScreening/gadnr/{slt}/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveThresholdScreeningGADNREnabled(@NotNull @PathVariable String slt) {
        Boolean result = sltPropertyProvider.getBoolean(SLTPropertyName.SLTFeatureThresholdScreeningGADNREnabled, slt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    
    
    /**
     * Retrieve Facility Naics enabled property
     * @return
     */
    @GetMapping(value = "/facilityNaics/{slt}/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveUserFeedbackEnabled(@NotNull @PathVariable String slt) {
    	
        Boolean result = sltPropertyProvider.getBoolean(SLTPropertyName.FacilityNaicsEnabled, slt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieve announcement enabled property
     * @return
     */
    @GetMapping(value = "/announcement/{slt}/enabled")
    @ResponseBody
    public ResponseEntity<Boolean> retrieveSltAnnouncementEnabled(@NotNull @PathVariable String slt) {
        Boolean result = sltPropertyProvider.getBoolean(SLTPropertyName.SLTFeatureAnnouncementEnabled, slt);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieve announcement text property
     * @return
     */
    @GetMapping(value = "/announcement/{slt}/text")
    @ResponseBody
    public ResponseEntity<PropertyDto> retrieveSltAnnouncementText(@NotNull @PathVariable String slt) {
        String result = sltPropertyProvider.getString(SLTPropertyName.SLTFeatureAnnouncementText, slt);
        return new ResponseEntity<>(new PropertyDto().withValue(result), HttpStatus.OK);
    }

}
