package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "calculation_material_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CalculationMaterialCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "description", length = 200)
    private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
