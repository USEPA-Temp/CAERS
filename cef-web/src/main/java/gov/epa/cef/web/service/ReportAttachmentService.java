/*
 * © Copyright 2019 EPA CAERS Project Team
 *
 * This file is part of the Common Air Emissions Reporting System (CAERS).
 *
 * CAERS is free software: you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License as published by the Free Software Foundation, 
 * either version 3 of the License, or (at your option) any later version.
 *
 * CAERS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with CAERS.  If 
 * not, see <https://www.gnu.org/licenses/>.
*/
package gov.epa.cef.web.service;

import java.io.OutputStream;
import gov.epa.cef.web.service.dto.ReportAttachmentDto;
import gov.epa.cef.web.util.TempFile;

public interface ReportAttachmentService {
	
	/***
     * Return attachment for the chosen attachment id
     * @param id
     * @return
     */
	 ReportAttachmentDto findAttachmentById(Long id);
	
	/***
     * Write file to output stream
     * @param id
     * @return
     */
	void writeFileTo(Long id, OutputStream outputStream);
    
    /**
     * Save a report attachment
     * @param dto
     * @return
     */
    ReportAttachmentDto saveAttachment(TempFile file, ReportAttachmentDto metadata);
    
    /**
     * Delete an report attachment record for a given id
     * @param id
     */
    void deleteAttachment(Long id);

}