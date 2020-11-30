package gov.epa.cef.web.provider.system;

import gov.epa.cef.web.domain.SLTConfigProperty;
import gov.epa.cef.web.exception.NotExistException;
import gov.epa.cef.web.repository.SLTConfigRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SLTPropertyProvider {

    @Autowired
    private SLTConfigRepository propertyRepo;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public String getString(IPropertyKey propertyKey, String programSystemCode) {

        SLTConfigProperty property = this.retrieve(propertyKey, programSystemCode);

        return property.getValue();
    }

    public SLTConfigProperty retrieve(IPropertyKey propertyKey, String programSystemCode) {

        String name = propertyKey.configKey();

        SLTConfigProperty property = this.propertyRepo.findByNameAndProgramSystemCodeCode(name, programSystemCode).orElseThrow(() -> {

            return new NotExistException("SltConfigProperty", name);
        });

        return property;
    }

    public List<SLTConfigProperty> retrieveAll() {

        return this.propertyRepo.findAll();
    }

    public List<SLTConfigProperty> retrieveAllForProgramSystem(String programSystemCode) {

        return this.propertyRepo.findByProgramSystemCodeCode(programSystemCode);
    }

    public SLTConfigProperty update(Long id, String value) {

        logger.info("Updating system property '{}' = '{}'", id, value);

        SLTConfigProperty property = this.propertyRepo.findById(id).orElseThrow(() -> {

            return new NotExistException("SltConfigProperty", id);
        });

        property.setValue(value);

        return this.propertyRepo.save(property);
    }

    public SLTConfigProperty update(IPropertyKey propertyKey, String programSystemCode, String value) {

        String name = propertyKey.configKey();

        logger.info("Updating system property '{}, {}' = '{}'", name, programSystemCode, value);

        SLTConfigProperty property = this.retrieve(propertyKey, programSystemCode);

        property.setValue(value);

        return this.propertyRepo.save(property);
    }

}
