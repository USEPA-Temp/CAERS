package gov.epa.cef.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.dto.MasterFacilityRecordDto;
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;
import gov.epa.cef.web.service.MasterFacilityRecordService;

@Service
public class MasterFacilityRecordServiceImpl implements MasterFacilityRecordService {

    @Autowired
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private MasterFacilityRecordMapper mapper;


    public MasterFacilityRecordDto findById(Long id) {

        return mfrRepo.findById(id)
                .map(this.mapper::toDto)
                .orElse(null);
    }

    public List<MasterFacilityRecordDto> findByProgramSystemCode(String programSystemCode) {

        List<MasterFacilityRecord> entities = this.mfrRepo.findByProgramSystemCodeCode(programSystemCode);
        return this.mapper.toDtoList(entities);
    }

    public MasterFacilityRecord transformFacilitySite(FacilitySiteDto fs) {

        MasterFacilityRecord mfr = new MasterFacilityRecord();
        this.mapper.updateFromDto(this.mapper.facilitySiteDtoToDto(fs), mfr);
        return mfr;
    }


    public MasterFacilityRecordDto update(MasterFacilityRecordDto dto) {

    	MasterFacilityRecord masterFacilityRecord = mfrRepo.findById(dto.getId()).orElse(null);
    	mapper.updateFromDto(dto, masterFacilityRecord);
    	MasterFacilityRecordDto result = mapper.toDto(mfrRepo.save(masterFacilityRecord));

    	return result;
    }

}
