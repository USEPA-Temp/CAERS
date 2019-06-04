package gov.epa.cef.web.service;

import java.util.Collection;

import gov.epa.cef.web.domain.ReleasePoint;

public interface ReleasePointService {

    /**
     * Retrieve Release Point by id
     * @param releasePointId 
     * @return
     */
    ReleasePoint retrieveById(Long releasePointId);

    /**
     * Retrieve Release Points by facility id
     * @param facilityId
     * @return
     */
    Collection<ReleasePoint> retrieveByFacilityId(Long facilityId);

}