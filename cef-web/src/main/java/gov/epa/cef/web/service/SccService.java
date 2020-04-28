package gov.epa.cef.web.service;

import gov.epa.cef.web.domain.PointSourceSccCode;

public interface SccService {

    /**
     * Retrieve Point SCCs from the webservice since a certain date and update the database with them
     * @param lastUpdated
     * @return
     */
    Iterable<PointSourceSccCode> updatePointSourceSccCodes(String lastUpdated);

}