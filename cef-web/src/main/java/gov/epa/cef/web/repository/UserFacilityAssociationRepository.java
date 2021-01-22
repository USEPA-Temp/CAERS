package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.UserFacilityAssociation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFacilityAssociationRepository extends CrudRepository<UserFacilityAssociation, Long> {

    List<UserFacilityAssociation> findByMasterFacilityRecordId(Long masterFacilityRecordId);

    List<UserFacilityAssociation> findByUserRoleId(Long userRoleId);

    @Query("select ufa from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr join mfr.programSystemCode psc where psc.code = :code")
    List<UserFacilityAssociation> findByProgramSystemCode(@Param("code") String code);

    @Query("select ufa from UserFacilityAssociation ufa join ufa.masterFacilityRecord mfr join mfr.programSystemCode psc where psc.code = :code and ufa.approved = :approved")
    List<UserFacilityAssociation> findByProgramSystemCodeAndApproved(@Param("code") String code, @Param("approved") boolean approved);

}
