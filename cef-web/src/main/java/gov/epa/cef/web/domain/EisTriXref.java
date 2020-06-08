package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseEntity;

@Entity
@Table(name = "eis_tri_xref")
public class EisTriXref extends BaseEntity {
	
    private static final long serialVersionUID = 1L;
    
    @Column(name = "eis_id", length = 22)
    private String eisId;

    @Column(name = "trifid", length = 15)
    private String trifid;

	public String getEisId() {
		return eisId;
	}

	public void setEisId(String eisId) {
		this.eisId = eisId;
	}

	public String getTrifid() {
		return trifid;
	}

	public void setTrifid(String trifid) {
		this.trifid = trifid;
	}

}
