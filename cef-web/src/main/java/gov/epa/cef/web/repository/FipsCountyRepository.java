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
import java.util.Optional;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import gov.epa.cef.web.domain.FipsCounty;

public interface FipsCountyRepository extends CrudRepository<FipsCounty, String> {

    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    List<FipsCounty> findAll(Sort sort);
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fc from FipsCounty fc where fc.lastInventoryYear = null or fc.lastInventoryYear >= :year")
    List<FipsCounty> findAllCurrent(@Param("year") int year, Sort sort);
    
    @QueryHints({
        @QueryHint(name = "org.hibernate.cacheable", value = "true")})
    @Query("select fc from FipsCounty fc where fc.fipsStateCode.code = :stateCode and (fc.lastInventoryYear = null or fc.lastInventoryYear >= :year)")
    List<FipsCounty> findCurrentByFipsStateCodeCode(@Param("stateCode") String stateCode, @Param("year") int year, Sort sort);

    List<FipsCounty> findByFipsStateCodeCode(String stateCode, Sort sort);

    Optional<FipsCounty> findByFipsStateCodeCodeAndName(String stateCode, String name);

    Optional<FipsCounty> findByFipsStateCodeCodeAndCountyCode(String stateCode, String countyCode);

}
