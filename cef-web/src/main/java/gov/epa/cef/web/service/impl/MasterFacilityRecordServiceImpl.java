package gov.epa.cef.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.epa.cef.web.domain.MasterFacilityRecord;
import gov.epa.cef.web.repository.MasterFacilityRecordRepository;
import gov.epa.cef.web.service.dto.FacilitySiteDto;
import gov.epa.cef.web.service.mapper.MasterFacilityRecordMapper;

@Service
public class MasterFacilityRecordServiceImpl {

    @Autowired
    private MasterFacilityRecordRepository mfrRepo;

    @Autowired
    private MasterFacilityRecordMapper mapper;


    public MasterFacilityRecord transformFacilitySite(FacilitySiteDto fs) {

        MasterFacilityRecord mfr = new MasterFacilityRecord();
        this.mapper.updateFromDto(this.mapper.facilitySiteDtoToDto(fs), mfr);
        return mfr;
    }
    
}
