package gov.epa.cef.web.client.api;

import gov.epa.cef.web.config.FrsConfig;
import gov.epa.client.frs.iptquery.ApiClient;
import gov.epa.client.frs.iptquery.FrsApi;
import gov.epa.client.frs.iptquery.model.Contact;
import gov.epa.client.frs.iptquery.model.Naics;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import gov.epa.client.frs.iptquery.model.ProgramGIS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiPredicate;
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

        return this.client.queryContact(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(c -> new ProgramIdTest(eisProgramId).test(c.getProgramSystemAcronym(), c.getProgramSystemId()))
            .collect(Collectors.toList());
    }

    public Collection<Naics> queryNaics(@NotNull String eisProgramId) {

        return this.client.queryNaics(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(n -> new ProgramIdTest(eisProgramId).test(n.getProgramSystemAcronym(), n.getProgramSystemId()))
            .collect(Collectors.toList());
    }

    public Optional<ProgramFacility> queryProgramFacility(@NotNull String eisProgramId) {

        return this.client.queryProgramFacility(this.config.getNaasUser(), this.config.getNaasPassword(),
            null, EISProgramAcronym, eisProgramId).stream()
            .filter(pf -> new ProgramIdTest(eisProgramId).test(pf.getProgramSystemAcronym(), pf.getProgramSystemId()))
            .findFirst();
    }

    public Optional<ProgramGIS> queryProgramGis(@NotNull String eisProgramId) {

        return this.client.queryProgramGis(this.config.getNaasUser(), this.config.getNaasPassword(),
            EISProgramAcronym, eisProgramId).stream()
            .filter(pg -> new ProgramIdTest(eisProgramId).test(pg.getProgramSystemAcronym(), pg.getProgramSystemId()))
            .findFirst();
    }

    private static class ProgramIdTest implements BiPredicate<String, String> {
        /*
        This BiPredicate is required to filter for the particular Acronym/ProgramId
        that we specifically want to retrieve.

        Background:
        The /QueryXXXX web services are implicitly wildcard searches.

        For example:
        /QueryProgramFacility?programSystemId=536311&programSystemAcronym=EI

        note EI and not EIS, the response from FRS will return data:

        [{"programSystemAcronym": "EIS", "programSystemId": "536311", ..snip..},
        {"programSystemAcronym": "EIS", "programSystemId": "5363111", ..snip..}]

        note EIS came back as well as an extra programId 536111
         */

        private final String programId;

        ProgramIdTest(String programId) {

            this.programId = programId;
        }

        @Override
        public boolean test(String acronym, String id) {

            return EISProgramAcronym.equals(acronym) && this.programId.equals(id);
        }
    }
}
