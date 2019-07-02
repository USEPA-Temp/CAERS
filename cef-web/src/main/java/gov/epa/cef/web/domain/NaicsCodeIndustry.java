package gov.epa.cef.web.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author amahfouz
 */
@Entity
@Table(name = "naics_code_industry")

public class NaicsCodeIndustry implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "code_prefix", unique = true, nullable = false, precision = 4, scale = 0)
    private Integer codePrefix;

    @Column(name = "industry", length = 200)
    private String industry;

    public Integer getCodePrefix() {
        return codePrefix;
    }

    public void setCodePrefix(Integer codePrefix) {
        this.codePrefix = codePrefix;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

}