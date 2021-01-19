package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseAuditEntity;

@Entity
@Table(name = "user_facility_association")
public class UserFacilityAssociation extends BaseAuditEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_facility_id", nullable = false)
    private MasterFacilityRecord masterFacilityRecord;

    @Column(name = "user_role_id", nullable = false)
    private Long userRoleId;

    @Column(name = "approved", nullable = false)
    private Boolean approved;

    public MasterFacilityRecord getMasterFacilityRecord() {
        return masterFacilityRecord;
    }

    public void setMasterFacilityRecord(MasterFacilityRecord masterFacilityRecord) {
        this.masterFacilityRecord = masterFacilityRecord;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

}
