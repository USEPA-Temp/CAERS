package gov.epa.cef.web.service.validation;

import com.baidu.unbiz.fluentvalidator.Validator;
import com.baidu.unbiz.fluentvalidator.ValidatorChain;
import com.baidu.unbiz.fluentvalidator.registry.Registry;
import gov.epa.cef.web.service.validation.validator.federal.EmissionsReportValidator;
import gov.epa.cef.web.service.validation.validator.state.GeorgiaValidator;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This Registry works with @FluentValidate annotation
 * The class(es) in @FluentValidate are looked up in the Spring context
 * Spring context lookup allows dependency injection of service/repository layer
 * into the validators
 */
@Component
public class ValidationRegistry implements Registry {

    private final ApplicationContext applicationContext;

    @Autowired
    ValidationRegistry(ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    public ValidatorChain createReportChain() {

        ValidatorChain chain = new ValidatorChain();

        List<Validator> validators = new ArrayList<>();

        validators.addAll(findByType(EmissionsReportValidator.class));
        validators.addAll(findByType(GeorgiaValidator.class));

        // add more emissions report / top level state validators here

        chain.setValidators(validators);

        return chain;
    }

    @Override
    public <T> List<T> findByType(Class<T> aClass) {

        Map<String, T> map =
            BeanFactoryUtils.beansOfTypeIncludingAncestors(this.applicationContext, aClass);

        return new ArrayList<>(map.values());
    }
}
