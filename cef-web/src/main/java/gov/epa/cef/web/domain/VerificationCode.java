package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * VerificationCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "verification_code")
public class VerificationCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public VerificationCode() {
    }

}