package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "emission_factor_variable_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class EmissionFactorVariableCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_type")
    private EfVariableValidationType validationType;

    public EfVariableValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(EfVariableValidationType validationType) {
        this.validationType = validationType;
    }

}
