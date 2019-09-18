package gov.epa.cef.web.client.api;

import gov.epa.cef.web.config.FrsConfig;
import gov.epa.client.frs.iptquery.ApiClient;
import gov.epa.client.frs.iptquery.FrsApi;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FrsApiClient {

    private static final String EISProgramAcronym = "EIS";

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

    public Collection<Contact> queryContacts(@NotNull String eisProgramId) {

        return this.client.queryContactGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(contact -> eisProgramId.equals(contact.getProgramSystemId()))
            .collect(Collectors.toList());
    }

    public Collection<Naics> queryNaics(@NotNull String eisProgramId) {

        return this.client.queryNaicsGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(naic -> eisProgramId.equals(naic.getProgramSystemId()))
            .collect(Collectors.toList());
    }

    public Optional<ProgramFacility> queryProgramFacility(@NotNull String eisProgramId) {

        return this.client.queryProgramFacilityGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            null, EISProgramAcronym, eisProgramId).stream()
            .filter(pf -> eisProgramId.equals(pf.getProgramSystemId()))
            .findFirst();
    }
}
