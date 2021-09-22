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
package gov.epa.cef.web.provider.system;

import gov.epa.cef.web.domain.ProgramSystemCode;
import gov.epa.cef.web.domain.SLTConfigProperty;
import gov.epa.cef.web.domain.SLTProperty;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.ProgramSystemCodeRepository;
import gov.epa.cef.web.repository.SLTConfigRepository;
import gov.epa.cef.web.repository.SLTPropertyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SLTPropertyProvider {

    @Autowired
    private SLTConfigRepository sltPropertyRepo;
    
    @Autowired
    private SLTPropertyRepository propertyRepo;
    
    @Autowired
    private ProgramSystemCodeRepository programRepo;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public String getString(IPropertyKey propertyKey, String programSystemCode) {

        SLTConfigProperty property = this.retrieve(propertyKey, programSystemCode);

        return property.getValue();
    }
    
    public boolean getBoolean(IPropertyKey propertyKey, String programSystemCode) {

        String strValue = this.getString(propertyKey, programSystemCode);

        return Boolean.valueOf(strValue);
    }

    public SLTConfigProperty retrieve(IPropertyKey propertyKey, String programSystemCode) {

        String name = propertyKey.configKey();
        
        SLTConfigProperty property = this.sltPropertyRepo.findByNameAndProgramSystemCodeCode(name, programSystemCode).orElse(null);
        
        // Create SLT Property for SLT if property does not exist
        if (property == null) {
        	createDefaultSLTProperty(name, programSystemCode);
        	property = this.sltPropertyRepo.findByNameAndProgramSystemCodeCode(name, programSystemCode).orElse(null);
        }
        
        return property;
    }

    public List<SLTConfigProperty> retrieveAll() {

        return this.sltPropertyRepo.findAll();
    }

    public List<SLTConfigProperty> retrieveAllForProgramSystem(String programSystemCode) {
    	List<SLTProperty> availableSLTProperties = (List<SLTProperty>) this.propertyRepo.findAll();
    	List<SLTConfigProperty> sltProperties = this.sltPropertyRepo.findByProgramSystemCodeCode(programSystemCode);
    	
    	if (availableSLTProperties.size() != sltProperties.size()) {
    		for (SLTProperty prop: availableSLTProperties) {
    			List<SLTConfigProperty> sltPropList = sltProperties.stream()
    					.filter(p -> p.getSltPropertyDetails().getName().equals(prop.getName()))
    					.collect(Collectors.toList());
    			
    			// Create SLT Property for SLT if property does not exist
    			if (sltProperties.isEmpty() || sltPropList.isEmpty()) {
    				createDefaultSLTProperty(prop.getName(), programSystemCode);
    			}
    		}
    		sltProperties = this.sltPropertyRepo.findByProgramSystemCodeCode(programSystemCode);
    	}
        return sltProperties;
    }

    public SLTConfigProperty update(Long id, String value) {

        logger.info("Updating system property '{}' = '{}'", id, value);

        SLTConfigProperty property = this.sltPropertyRepo.findById(id).orElseThrow(() -> {

            return new NotExistException("SltConfigProperty", id);
        });

        property.setValue(value);

        return this.sltPropertyRepo.save(property);
    }

    public SLTConfigProperty update(IPropertyKey propertyKey, String programSystemCode, String value) {

        String name = propertyKey.configKey();

        logger.info("Updating system property '{}, {}' = '{}'", name, programSystemCode, value);

        SLTConfigProperty property = this.retrieve(propertyKey, programSystemCode);

        property.setValue(value);

        return this.sltPropertyRepo.save(property);
    }
    
    public SLTConfigProperty createDefaultSLTProperty(String name, String programSystemCode) {

        logger.info("Updating system property '{}, {}'", name, programSystemCode);

        SLTConfigProperty configProperty = new SLTConfigProperty();
        SLTProperty prop = propertyRepo.findById(name).orElseThrow(() -> {
            return new NotExistException("SLTProperty", name);
        });
        ProgramSystemCode psc = programRepo.findById(programSystemCode).orElseThrow(() -> {
            return new NotExistException("ProgramSystemCode", programSystemCode);
        });
        
        configProperty.setSLTProperty(prop);
        configProperty.setProgramSystemCode(psc);
        
        // Set default value to TRUE when creating boolean property
        if (configProperty.getSltPropertyDetails().getDatatype().equalsIgnoreCase("boolean")) {
        	configProperty.setValue(Boolean.TRUE.toString());
        } else {
        	// Set default string value to null when creating property
        	configProperty.setValue(null);
        }
        
        return this.sltPropertyRepo.save(configProperty);
    }

}
