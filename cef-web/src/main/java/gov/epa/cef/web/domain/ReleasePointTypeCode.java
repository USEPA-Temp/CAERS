package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

@Entity
@Table(name = "release_point_type_Code")
public class ReleasePointTypeCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

}