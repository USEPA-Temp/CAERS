package gov.epa.cef.web.service.impl;

import gov.epa.cef.web.client.api.FrsApiClient;
import gov.epa.cef.web.client.api.FrsApiTransforms;
import gov.epa.cef.web.domain.FacilitySite;
import gov.epa.cef.web.exception.ApplicationErrorCode;
import gov.epa.cef.web.exception.ApplicationException;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.service.FacilitySiteService;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.FacilitySiteMapper;
import gov.epa.client.frs.iptquery.model.ProgramFacility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilitySiteServiceImpl implements FacilitySiteService {

    private final EmissionsReportRepository emissionsReportRepository;

    private final FacilitySiteRepository facSiteRepo;

    private final FacilitySiteMapper facilitySiteMapper;

    private final FrsApiClient frsClient;

    @Autowired
    FacilitySiteServiceImpl(FacilitySiteRepository facSiteRepo,
                            EmissionsReportRepository emissionsReportRepository,
                            FacilitySiteMapper facilitySiteMapper,
                            FrsApiClient frsClient) {

        this.facSiteRepo = facSiteRepo;
        this.emissionsReportRepository = emissionsReportRepository;
        this.facilitySiteMapper = facilitySiteMapper;
        this.frsClient = frsClient;
    }

    @Override
    public Optional<FacilitySite> copyFromFrs(long emissionsReportId, String facilityEisProgramId) {

        return this.emissionsReportRepository.findById(emissionsReportId)
            .map(report -> {

                FacilitySite facilitySite = retrieveFromFrs(report.getEisProgramId())
                    .map(FrsApiTransforms.toFacilitySite(report))
                    .orElseThrow(() -> new ApplicationException(ApplicationErrorCode.E_INVALID_ARGUMENT,
                        String.format("EIS Program ID %s does not exist in FRS.", report.getEisProgramId())));

                facilitySite.getContacts().addAll(frsClient.queryContacts(report.getEisProgramId()).stream()
                    .map(FrsApiTransforms.toFacilitySiteContact(facilitySite))
                    .collect(Collectors.toList()));

                facilitySite.getFacilityNAICS().addAll(frsClient.queryNaics(report.getEisProgramId()).stream()
                    .map(FrsApiTransforms.toFacilityNaicsXref(facilitySite))
                    .collect(Collectors.toList()));

                return this.facSiteRepo.save(facilitySite);
            });
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
