package gov.epa.cef.web.client.api;

import gov.epa.cef.web.config.FrsConfig;
import gov.epa.cef.web.util.ProgramSystemAcronyms;
import gov.epa.client.frs.iptquery.ApiClient;
import gov.epa.client.frs.iptquery.FrsApi;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class FrsApiClient {

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

    public Collection<Contact> queryContacts(String eisProgramId) {

        return this.client.queryContactGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            ProgramSystemAcronyms.EIS.name(), eisProgramId);
    }

    public Collection<Naics> queryNaics(String eisProgramId) {

        return this.client.queryNaicsGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            ProgramSystemAcronyms.EIS.name(), eisProgramId);
    }

    public Optional<ProgramFacility> queryProgramFacility(String eisProgramId) {

        return this.client.queryProgramFacilityGet(this.config.getNaasUser(), this.config.getNaasPassword(),
            null, ProgramSystemAcronyms.EIS.name(), eisProgramId).stream().findFirst();
    }
}
