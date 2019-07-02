package gov.epa.cef.web.service;

import java.util.List;

import gov.epa.cef.web.service.dto.ReleasePointDto;

public interface ReleasePointService {

    /**
     * Retrieve Release Point by id
     * @param releasePointId 
     * @return
     */
    ReleasePointDto retrieveById(Long releasePointId);

    /**
     * Retrieve Release Points by facility id
     * @param facilityId
     * @return
     */
    List<ReleasePointDto> retrieveByFacilitySiteId(Long facilitySiteId);

}