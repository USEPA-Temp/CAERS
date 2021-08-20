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

import gov.epa.cef.web.config.CacheName;
import gov.epa.cef.web.domain.UserFacilityAssociation;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFacilityAssociationRepository extends CrudRepository<UserFacilityAssociation, Long> {

    List<UserFacilityAssociation> findByMasterFacilityRecordId(Long masterFacilityRecordId);

    List<UserFacilityAssociation> findByMasterFacilityRecordIdAndApproved(Long masterFacilityRecordId, boolean approved);

    List<UserFacilityAssociation> findByUserRoleId(Long userRoleId);

    List<UserFacilityAssociation> findByUserRoleIdAndApproved(Long userRoleId, boolean approved);

    @Query("select ufa from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr join mfr.programSystemCode psc where psc.code = :code")
    List<UserFacilityAssociation> findByProgramSystemCode(@Param("code") String code);

    @Query("select ufa from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr join mfr.programSystemCode psc where psc.code = :code and ufa.approved = :approved")
    List<UserFacilityAssociation> findByProgramSystemCodeAndApproved(@Param("code") String code, @Param("approved") boolean approved);

    @Query("select mfr.eisProgramId from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr where ufa.userRoleId = :userRoleId and ufa.approved = true")
    List<String> retrieveMasterFacilityRecordProgramIds(@Param("userRoleId") Long userRoleId);

    @Cacheable(value = CacheName.UserMasterFacilityIds)
    @Query("select mfr.id from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr where ufa.userRoleId = :userRoleId and ufa.approved = true")
    List<Long> retrieveMasterFacilityRecordIds(@Param("userRoleId") Long userRoleId);

}
