package gov.epa.cef.web.client.api;

import javax.validation.constraints.NotBlank;
import java.util.function.Predicate;

public class FrsPredicates {

    public static Predicate<FrsProgramIdentifiable> identifiable(@NotBlank String eisProgramId) {

        /*
        This Predicate is required to filter for the particular Acronym/ProgramId
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

        return pi -> FrsApiClient.EIS_PROGRAM_ACRONYM.equals(pi.getProgramSystemAcronym())
            && eisProgramId.equals(pi.getProgramSystemId());
    }
}
