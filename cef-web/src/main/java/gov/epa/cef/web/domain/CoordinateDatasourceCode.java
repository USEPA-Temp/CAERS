package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * CoordinateDatasourceCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "coordinate_datasource_code")
public class CoordinateDatasourceCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public CoordinateDatasourceCode() {
    }

}