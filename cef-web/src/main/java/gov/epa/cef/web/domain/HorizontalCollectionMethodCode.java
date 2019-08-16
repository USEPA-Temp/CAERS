package gov.epa.cef.web.domain;

import gov.epa.cef.web.domain.common.BaseLookupEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * HorizontalCollectionMethodCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "horizontal_collection_method_code")
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class HorizontalCollectionMethodCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}
