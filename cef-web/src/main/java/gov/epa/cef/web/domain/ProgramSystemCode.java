package gov.epa.cef.web.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import gov.epa.cef.web.domain.common.BaseLookupEntity;

/**
 * ProgramSystemCode entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "program_system_code")
public class ProgramSystemCode extends BaseLookupEntity {

    private static final long serialVersionUID = 1L;

    // Constructors

    /** default constructor */
    public ProgramSystemCode() {
    }

}