package gov.epa.cef.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "slt_config")
public class SLTConfigProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlElement(name = "id")
    protected Long id;

    @Column(name = "name", nullable = false, length = 64)
    protected String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_system_code")
    private ProgramSystemCode programSystemCode;

    @Column(name = "value", length = 2000)
    protected String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgramSystemCode getProgramSystemCode() {
        return programSystemCode;
    }

    public void setProgramSystemCode(ProgramSystemCode programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
