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
package gov.epa.cef.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gov.epa.cef.web.domain.EmissionFormulaVariable;

public interface EmissionFormulaVariableRepository extends CrudRepository<EmissionFormulaVariable, String> {
	   
	   /**
	    * Retrieve a list of all emission formula variables for a specific program system code and emissions reporting year
	    * @param psc Program System Code
	    * @param emissionsReportYear
	    * @return
	    */
	   @Query("select efv from EmissionFormulaVariable efv join efv.emission e join e.reportingPeriod rp join rp.emissionsProcess ep join ep.emissionsUnit eu join eu.facilitySite fs join fs.emissionsReport er where er.programSystemCode.code = :psc and er.year = :emissionsReportYear")
	   List<EmissionFormulaVariable> findByPscAndEmissionsReportYear(String psc, Short emissionsReportYear);

}
