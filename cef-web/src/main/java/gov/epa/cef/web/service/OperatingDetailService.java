package gov.epa.cef.web.service;

import gov.epa.cef.web.service.dto.OperatingDetailDto;

public interface OperatingDetailService {

    /**
     * Update an Operating Detail
     * @param dto
     * @return
     */
    OperatingDetailDto update(OperatingDetailDto dto);

}