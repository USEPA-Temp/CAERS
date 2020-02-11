package gov.epa.cef.web.client.api;

import gov.epa.client.frs.iptquery.ApiClient;
import gov.epa.client.frs.iptquery.FrsApi;
import gov.epa.client.frs.iptquery.model.Association;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Control;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.Pollutant;
import gov.epa.client.frs.iptquery.model.Process;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import gov.epa.client.frs.iptquery.model.ProgramGIS;
import gov.epa.client.frs.iptquery.model.ReleasePoint;
import gov.epa.client.frs.iptquery.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FrsApiClient {

    private final FrsApi client;

    private final FrsConfig config;

    static final String EISProgramAcronym = "EIS";

    @Autowired
    FrsApiClient(FrsConfig config) {

        super();

        this.config = config;

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(this.config.getFacilityIptEndpoint().toString());
        if (this.config.isDebugging()) {
            apiClient.setDebugging(true);
        }

        this.client = new FrsApi(apiClient);
    }

    public Collection<Association> queryAssociation(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryAssociation(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Contact> queryContacts(@NotBlank String eisProgramId) {

        return this.client.queryContact(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Control> queryEmissionsControl(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryEmissionsControl(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Pollutant> queryPollutant(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryPollutant(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Process> queryEmissionsProcess(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryEmissionsProcess(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Unit> queryEmissionsUnit(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryEmissionsUnit(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Collection<Naics> queryNaics(@NotBlank String eisProgramId) {

        return this.client.queryNaics(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Optional<ProgramFacility> queryProgramFacility(@NotBlank String eisProgramId) {

        // use the first one, assumption is that only one is available after filter
        return this.client.queryProgramFacility(this.config.getNaasUser(), this.config.getNaasPassword(),
            null, EISProgramAcronym, eisProgramId).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .findFirst();
    }

    public Optional<ProgramGIS> queryProgramGis(@NotBlank String eisProgramId) {

        // use the first one, assumption is that only one is available after filter
        return this.client.queryProgramGis(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .findFirst();
    }

    public Collection<ReleasePoint> queryReleasePoint(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryReleasePoint(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    @Component
    @Validated
    @ConfigurationProperties(prefix = "frs")
    public static class FrsConfig {

        private boolean debugging = false;

        @NotNull
        private URL facilityIptEndpoint;

        @NotBlank
        private String naasPassword;

        @NotBlank
        private String naasUser;

        public URL getFacilityIptEndpoint() {

            return facilityIptEndpoint;
        }

        public void setFacilityIptEndpoint(URL facilityIptEndpoint) {

            this.facilityIptEndpoint = facilityIptEndpoint;
        }

        public String getNaasPassword() {

            return naasPassword;
        }

        public void setNaasPassword(String naasPassword) {

            this.naasPassword = naasPassword;
        }

        public String getNaasUser() {

            return naasUser;
        }

        public void setNaasUser(String naasUser) {

            this.naasUser = naasUser;
        }

        public boolean isDebugging() {

            return debugging;
        }

        public void setDebugging(boolean debugging) {

            this.debugging = debugging;
        }
    }
}
