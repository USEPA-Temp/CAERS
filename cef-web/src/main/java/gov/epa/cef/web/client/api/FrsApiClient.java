package gov.epa.cef.web.client.api;

import gov.epa.cef.web.config.FrsConfig;
import gov.epa.client.frs.iptquery.ApiClient;
import gov.epa.client.frs.iptquery.FrsApi;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import gov.epa.client.frs.iptquery.model.ProgramGIS;
import gov.epa.client.frs.iptquery.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FrsApiClient {

    static final String EISProgramAcronym = "EIS";

    private final FrsApi client;

    private final FrsConfig config;

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

    public Collection<Contact> queryContacts(@NotBlank String eisProgramId) {

        return this.client.queryContact(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
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

    public Collection<Unit> queryEmissionsUnit(@NotBlank String eisProgramId, Integer pageSize, Integer offset) {

        return this.client.queryEmissionsUnit(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId, pageSize, offset).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .collect(Collectors.toList());
    }

    public Optional<ProgramGIS> queryProgramGis(@NotBlank String eisProgramId) {

        // use the first one, assumption is that only one is available after filter
        return this.client.queryProgramGis(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(FrsPredicates.identifiable(eisProgramId))
            .findFirst();
    }
}
