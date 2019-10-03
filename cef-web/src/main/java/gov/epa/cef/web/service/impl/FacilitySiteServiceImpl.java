package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.api.FrsApiClient;
import gov.epa.cef.web.client.api.FrsFacilityApiTransforms;
import gov.epa.cef.web.client.api.FrsSubfacilityTransforms;
import gov.epa.cef.web.domain.EmissionsReport;
import gov.epa.cef.web.domain.EmissionsUnit;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import gov.epa.client.frs.iptquery.model.Association;
import gov.epa.client.frs.iptquery.model.Process;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FacilitySiteServiceImpl implements FacilitySiteService {

    private final FacilitySiteRepository facSiteRepo;

    private final FacilitySiteMapper facilitySiteMapper;

    private final FrsApiClient frsClient;

    @Autowired
    FacilitySiteServiceImpl(FacilitySiteRepository facSiteRepo,
                            FacilitySiteMapper facilitySiteMapper,
                            FrsApiClient frsClient) {

        this.facSiteRepo = facSiteRepo;
        this.facilitySiteMapper = facilitySiteMapper;
        this.frsClient = frsClient;
    }

    @Override
    public FacilitySite copyFromFrs(EmissionsReport report) {

        String eisProgramId = report.getEisProgramId();

        FacilitySite facilitySite = retrieveFromFrs(eisProgramId)
            .map(FrsFacilityApiTransforms.toFacilitySite(report))
            .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
                String.format("EIS Program ID %s does not exist in FRS.", report.getEisProgramId())));

        this.frsClient.queryProgramGis(eisProgramId)
            .ifPresent(gis -> {

                facilitySite.setLatitude(gis.getLatitude());
                facilitySite.setLongitude(gis.getLongitude());
            });

        facilitySite.getContacts().addAll(this.frsClient.queryContacts(eisProgramId).stream()
            .map(FrsFacilityApiTransforms.toFacilitySiteContact(facilitySite))
            .collect(Collectors.toList()));

        facilitySite.getFacilityNAICS().addAll(this.frsClient.queryNaics(eisProgramId).stream()
            .map(FrsFacilityApiTransforms.toFacilityNaicsXref(facilitySite))
            .collect(Collectors.toList()));

        facilitySite.getControls().addAll(
            this.frsClient.queryEmissionsControl(eisProgramId, null, null).stream()
                .map(FrsSubfacilityTransforms.toControl(facilitySite))
                .collect(Collectors.toList()));

        // TODO we need to allow nulls or there is either missing data in FRS responser
//        facilitySite.getReleasePoints().addAll(
//            this.frsClient.queryReleasePoint(eisProgramId, null, null).stream()
//                .map(FrsSubfacilityTransforms.toReleasePoint(facilitySite))
//                .collect(Collectors.toList()));

        Map<String, EmissionsUnit> emissionUnits =
            this.frsClient.queryEmissionsUnit(eisProgramId, null, null).stream()
                .map(FrsSubfacilityTransforms.toEmissionsUnit(facilitySite))
                .collect(Collectors.toMap(EmissionsUnit::getUnitIdentifier, u -> u));

        Map<String, Process> processes =
            this.frsClient.queryEmissionsProcess(eisProgramId, null, null).stream()
                .collect(Collectors.toMap(Process::getProcessId, p -> p));

        this.frsClient.queryAssociation(eisProgramId, null, null).stream()
            .collect(Collectors.groupingBy(Association::getSubComponentId1))
            .forEach((assoc1, associations) -> {

                if (emissionUnits.containsKey(assoc1)) {

                    EmissionsUnit emissionsUnit = emissionUnits.get(assoc1);
                    emissionsUnit.setEmissionsProcesses(associations.stream()
                        .filter(a -> processes.containsKey(a.getSubComponentId2()))
                        .map(a -> processes.get(a.getSubComponentId2()))
                        .map(FrsSubfacilityTransforms.toProcess(emissionsUnit))
                        .collect(Collectors.toList()));
                }
            });

        facilitySite.getEmissionsUnits().addAll(emissionUnits.values());

        return this.facSiteRepo.save(facilitySite);
    }

    @Override
    public FacilitySiteDto findByEisProgramIdAndReportId(String eisProgramId, Long emissionsReportId) {

        return facSiteRepo.findByEisProgramIdAndEmissionsReportId(eisProgramId, emissionsReportId)
            .stream()
            .findFirst()
            .map(facilitySiteMapper::toDto)
            .orElse(null);
    }

    @Override
    public FacilitySiteDto findById(Long id) {

        return facSiteRepo.findById(id)
            .map(facilitySiteMapper::toDto)
            .orElse(null);
    }

    @Override
    public Optional<ProgramFacility> retrieveFromFrs(String facilityEisProgramId) {

        return this.frsClient.queryProgramFacility(facilityEisProgramId);
    }
}
