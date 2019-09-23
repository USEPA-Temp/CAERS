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
@Table(name = "calculation_method_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class CalculationMethodCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "control_indicator", nullable = false)
    private Boolean controlIndicator;

    @Column(name = "epa_emission_factor", nullable = false)
    private Boolean epaEmissionFactor;

    @Column(name = "total_direct_entry", nullable = false)
    private Boolean totalDirectEntry;

    public Boolean getControlIndicator() {
        return controlIndicator;
    }

    public void setControlIndicator(Boolean controlIndicator) {
        this.controlIndicator = controlIndicator;
    }

    public Boolean getEpaEmissionFactor() {
        return epaEmissionFactor;
    }

    public void setEpaEmissionFactor(Boolean epaEmissionFactor) {
        this.epaEmissionFactor = epaEmissionFactor;
    }

    public Boolean getTotalDirectEntry() {
        return totalDirectEntry;
    }

    public void setTotalDirectEntry(Boolean totalDirectEntry) {
        this.totalDirectEntry = totalDirectEntry;
    }


}
