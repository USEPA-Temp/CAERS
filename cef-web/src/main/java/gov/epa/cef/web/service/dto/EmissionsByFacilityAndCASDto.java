package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmissionsByFacilityAndCASDto implements Serializable {
    
    /**
     * default version id
     */
    private static final long serialVersionUID = 1L;
    
    private String message;
    private String code;
    private String trifid;
    private String facilityName;
    private Short year;
    private String reportStatus;
    private String chemical;
    private String casNumber;
    private BigDecimal stackEmissions;
    private BigDecimal fugitiveEmissions;
    private String uom;
    private Date certificationDate;
    
    @JsonIgnore
    private Long reportId;
    
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getTrifid() {
		return trifid;
	}
	public void setTrifid(String trifid) {
		this.trifid = trifid;
	}
	public String getFacilityName() {
        return facilityName;
    }
    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }
    public Short getYear() {
        return year;
    }
    public void setYear(Short year) {
        this.year = year;
    }
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	public String getChemical() {
        return chemical;
    }
    public void setChemical(String chemical) {
        this.chemical = chemical;
    }
    public String getCasNumber() {
        return casNumber;
    }
    public void setCasNumber(String casNumber) {
        this.casNumber = casNumber;
    }
    public BigDecimal getStackEmissions() {
        return stackEmissions;
    }
    public void setStackEmissions(BigDecimal stackEmissions) {
        this.stackEmissions = stackEmissions;
    }
    public BigDecimal getFugitiveEmissions() {
        return fugitiveEmissions;
    }
    public void setFugitiveEmissions(BigDecimal fugitiveEmissions) {
        this.fugitiveEmissions = fugitiveEmissions;
    }
    public String getUom() {
        return uom;
    }
    public void setUom(String uom) {
        this.uom = uom;
    }
	public Date getCertificationDate() {
		return certificationDate;
	}
	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}
	public Long getReportId() {
		return reportId;
	}
	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}
}
