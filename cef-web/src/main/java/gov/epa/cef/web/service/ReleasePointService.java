package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.ReleasePointDto;

import java.util.List;

public interface ReleasePointService {
	
	/**
     * Create a new Facility Site Contact
     * @param dto
     * @return
     */
	ReleasePointDto create(ReleasePointDto dto);

    /**
     * Retrieve Release Point by id
     * @param releasePointId
     * @return
     */
    ReleasePointDto retrieveById(Long releasePointId);

    /**
     * Retrieve Release Points by facility id
     * @param facilitySiteId
     * @return
     */
    List<ReleasePointDto> retrieveByFacilitySiteId(Long facilitySiteId);
    
    /**
     * Delete Release Point by id
     * @param releasePointId
     */
    void delete(Long releasePointId);
    
    /**
     * Delete Release Point Apportionment by id
     * @param releasePointApptId
     */
    void deleteAppt(Long releasePointApptId);

}
