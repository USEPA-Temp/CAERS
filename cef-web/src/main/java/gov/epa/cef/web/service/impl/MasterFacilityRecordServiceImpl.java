package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.domain.common.BaseLookupEntity;
import gov.epa.cef.web.repository.FacilitySiteRepository;
import gov.epa.cef.web.repository.EmissionsReportRepository;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.service.dto.CodeLookupDto;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.mapper.LookupEntityMapper;
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;
import gov.epa.cef.web.service.MasterFacilityRecordService;

@Service
public class MasterFacilityRecordServiceImpl implements MasterFacilityRecordService {

    @Autowired
    private FacilitySiteRepository facilitySiteRepo;

    @Autowired
    private EmissionsReportRepository emissionsReportRepo;

    @Autowired
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private MasterFacilityRecordMapper mapper;

    @Autowired
    private LookupEntityMapper lookupMapper;


    public MasterFacilityRecordDto findById(Long id) {

        return mfrRepo.findById(id)
                .map(this.mapper::toDto)
                .orElse(null);
    }

    public List<MasterFacilityRecordDto> findByProgramSystemCode(String programSystemCode) {

        List<MasterFacilityRecord> entities = this.mfrRepo.findByProgramSystemCodeCode(programSystemCode);
        return this.mapper.toDtoList(entities);
    }

    public List<MasterFacilityRecordDto> findByExample(MasterFacilityRecordDto criteria) {

        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).withIgnoreCase();
        return this.mapper.toDtoList(this.mfrRepo.findAll(Example.of(this.mapper.fromDto(criteria), matcher), Sort.by(Direction.ASC, "name")));
    }

    public List<CodeLookupDto> findDistinctProgramSystems() {

        List<BaseLookupEntity> entities = this.mfrRepo.findDistinctProgramSystems();
        return this.lookupMapper.toDtoList(entities);
    }

    public MasterFacilityRecord transformFacilitySite(FacilitySiteDto fs) {

        MasterFacilityRecord mfr = new MasterFacilityRecord();
        this.mapper.updateFromDto(this.mapper.facilitySiteDtoToDto(fs), mfr);
        return mfr;
    }


    public MasterFacilityRecordDto update(MasterFacilityRecordDto dto) {

    	MasterFacilityRecord masterFacilityRecord = mfrRepo.findById(dto.getId()).orElse(null);
        mapper.updateFromDto(dto, masterFacilityRecord);
        MasterFacilityRecord updatedMasterFacilityRecord = mfrRepo.save(masterFacilityRecord);

        //Cascade changes to the master facility record to the facility site for each of the "In Progress" emissions reports
        emissionsReportRepo.findInProgressByMasterFacilityId(updatedMasterFacilityRecord.getId())
            .forEach(report -> report.getFacilitySites()
                .forEach(fs -> {
                    fs.setCity(updatedMasterFacilityRecord.getCity());
                    fs.setCountryCode(updatedMasterFacilityRecord.getCountryCode());
                    fs.setCountyCode(updatedMasterFacilityRecord.getCountyCode());
                    fs.setDescription(updatedMasterFacilityRecord.getDescription());
                    fs.setFacilityCategoryCode(updatedMasterFacilityRecord.getFacilityCategoryCode());
                    fs.setMailingCity(updatedMasterFacilityRecord.getMailingCity());
                    fs.setMailingPostalCode(updatedMasterFacilityRecord.getMailingPostalCode());
                    fs.setMailingStateCode(updatedMasterFacilityRecord.getMailingStateCode());
                    fs.setMailingStreetAddress(updatedMasterFacilityRecord.getMailingStreetAddress());
                    fs.setName(updatedMasterFacilityRecord.getName());
                    fs.setOperatingStatusCode(updatedMasterFacilityRecord.getOperatingStatusCode());
                    fs.setPostalCode(updatedMasterFacilityRecord.getPostalCode());
                    fs.setStateCode(updatedMasterFacilityRecord.getStateCode());
                    fs.setStatusYear(updatedMasterFacilityRecord.getStatusYear());
                    fs.setStreetAddress(updatedMasterFacilityRecord.getStreetAddress());
                    fs.setTribalCode(updatedMasterFacilityRecord.getTribalCode());
                    facilitySiteRepo.save(fs);
                }));

    	MasterFacilityRecordDto result = mapper.toDto(updatedMasterFacilityRecord);

    	return result;
    }

}
