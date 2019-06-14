package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

@Entity
@Table(name = "reporting_period_code")
public class ReportingPeriodCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "short_name", nullable = false, length = 50)
    private String shortName;

}
