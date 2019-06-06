package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * HorizontalCollectionMethodCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "horizontal_collection_method_code")
public class HorizontalCollectionMethodCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public HorizontalCollectionMethodCode() {
    }

}