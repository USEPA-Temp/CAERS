package gov.epa.cef.web.service.validation;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CefValidatorContext {

    private final Set<ValidationFeature> features;

    public CefValidatorContext() {

        this.features = new HashSet<>();
    }

    public CefValidatorContext disable(@NotNull ValidationFeature feature) {

        this.features.remove(feature);
        return this;
    }

    public CefValidatorContext disable(ValidationFeature[] features) {

        if (features != null) {
            this.features.removeAll(Arrays.asList(features));
        }

        return this;
    }

    public CefValidatorContext enable(ValidationFeature[] features) {

        if (features != null) {
            this.features.addAll(Arrays.asList(features));
        }

        return this;
    }

    public CefValidatorContext enable(@NotNull ValidationFeature feature) {

        this.features.add(feature);
        return this;
    }

    public boolean isEnabled(@NotNull ValidationFeature feature) {

        return this.features.contains(feature);
    }

    public boolean isNotEnabled(@NotNull ValidationFeature feature) {

        return isEnabled(feature) == false;
    }

    public CefValidatorContext reset() {

        this.features.clear();
        return this;
    }
}
