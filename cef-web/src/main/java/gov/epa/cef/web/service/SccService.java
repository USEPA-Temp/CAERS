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

import java.time.LocalDate;

import gov.epa.cef.web.domain.PointSourceSccCode;

public interface SccService {

    /**
     * Retrieve Point SCCs from the webservice since a certain date and update the database with them
     * @param lastUpdated
     * @return
     */
    Iterable<PointSourceSccCode> updatePointSourceSccCodes(LocalDate lastUpdated);

}