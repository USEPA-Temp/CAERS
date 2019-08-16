package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * GeometricTypeCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "geometric_type_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class GeometricTypeCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}
