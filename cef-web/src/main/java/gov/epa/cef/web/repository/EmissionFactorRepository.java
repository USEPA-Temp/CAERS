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

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.EmissionFactor;

public interface EmissionFactorRepository extends JpaRepository<EmissionFactor, Long> {

	@Query("select ef from EmissionFactor ef where ef.sccCode = :sccCode and ef.pollutantCode = :pollutantCode and ef.controlIndicator = :controlIndicator")
	List<EmissionFactor> findBySccCodePollutantControlIndicator(@Param("sccCode") String sccCode, @Param("pollutantCode") String pollutantCode, 
										  						@Param("controlIndicator") boolean controlIndicator);
	
	@Query("select ef from EmissionFactor ef where ef.sccCode = :sccCode and ef.pollutantCode = :pollutantCode and ef.emissionFactor = :emissionFactor and ef.controlIndicator = :controlIndicator")
	EmissionFactor findBySccCodePollutantEmissionFactorControlIndicator(@Param("sccCode") String sccCode, @Param("pollutantCode") String pollutantCode, 
																		@Param("emissionFactor") BigDecimal emissionFactor,
																		@Param("controlIndicator") boolean controlIndicator);
	
	@Query("select ef from EmissionFactor ef where ef.sccCode = :sccCode and ef.pollutantCode = :pollutantCode and ef.emissionFactorFormula = :emissionFactorFormula and ef.controlIndicator = :controlIndicator")
	EmissionFactor findBySccCodePollutantEmissionFactorFormulaControlIndicator(@Param("sccCode") String sccCode, @Param("pollutantCode") String pollutantCode, 
																		@Param("emissionFactorFormula") String emissionFactorFormula,
																		@Param("controlIndicator") boolean controlIndicator);
}
