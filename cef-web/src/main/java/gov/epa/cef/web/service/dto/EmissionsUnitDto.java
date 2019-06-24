package gov.epa.cef.web.service.dto;

import java.io.Serializable;
import java.util.List;

public class EmissionsUnitDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long facilitySiteId;
    private String unitTypeCodeDescription;
    private String operatingStatusCodeDescription;
    private String unitIdentifier;
    private String programSystemCode;
    private String description;
    private String typeCodeDescription;
    private Short statusYear;
    private CodeLookupDto unitOfMeasureCode;
    private String unitOfMeasureDescription;
    private String comments;
    private List<EmissionsProcessDto> emissionsProcesses;



    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUnitTypeCodeDescription() {
        return this.unitTypeCodeDescription;
    }
    public void setUnitTypeCodeDescription(String unitTypeCodeDescription) {
        this.unitTypeCodeDescription = unitTypeCodeDescription;
    }

    public String getOperatingStatusCodeDescription() {
        return this.operatingStatusCodeDescription;
    }
    public void setOperatingStatusCodeDescription(String operatingStatusCodeDescription) {
        this.operatingStatusCodeDescription = operatingStatusCodeDescription;
    }

    public Long getFacilitySiteId() {
        return this.facilitySiteId;
    }
    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
    }

    public String getUnitIdentifier() {
        return this.unitIdentifier;
    }
    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    public String getProgramSystemCode() {
        return this.programSystemCode;
    }
    public void setProgramSystemCode(String programSystemCode) {
        this.programSystemCode = programSystemCode;
    }

    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getTypeCodeDescription() {
        return this.typeCodeDescription;
    }
    public void setTypeCodeDescription(String typeCodeDescription) {
        this.typeCodeDescription = typeCodeDescription;
    }

    public Short getStatusYear() {
        return this.statusYear;
    }
    public void setStatusYear(Short statusYear) {
        this.statusYear = statusYear;
    }

    public CodeLookupDto getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }
    public void setUnitOfMeasureCode(CodeLookupDto unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public String getUnitOfMeasureDescription() {
        return this.unitOfMeasureDescription;
    }
    public void setUnitOfMeasureDescription(String unitOfMeasureDescription) {
        this.unitOfMeasureDescription = unitOfMeasureDescription;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<EmissionsProcessDto> getEmissionsProcesses() {
        return emissionsProcesses;
    }
    public void setEmissionsProcesses(List<EmissionsProcessDto> emissionsProcesses) {
        this.emissionsProcesses = emissionsProcesses;
    }

}