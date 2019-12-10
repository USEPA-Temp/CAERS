package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.ReleasePointDto;

import java.util.List;

public interface ReleasePointService {
	
	/**
     * Create a new Release Point
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
     * Update an existing Release Point by id
     * @param dto
     * @return
     */
    ReleasePointDto update(ReleasePointDto dto);
    
    /**
     * Delete Release Point by id
     * @param releasePointId
     */
    void delete(Long releasePointId);

}
