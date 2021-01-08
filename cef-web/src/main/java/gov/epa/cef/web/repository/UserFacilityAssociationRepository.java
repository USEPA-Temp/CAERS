package gov.epa.cef.web.repository;

import gov.epa.cef.web.domain.UserFacilityAssociation;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFacilityAssociationRepository extends CrudRepository<UserFacilityAssociation, Long> {

    List<UserFacilityAssociation> findByMasterFacilityRecordId(Long masterFacilityRecordId);

}
