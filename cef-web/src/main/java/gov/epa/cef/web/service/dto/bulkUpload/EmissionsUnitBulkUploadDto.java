package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class EmissionsUnitBulkUploadDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long facilitySiteId;
    private String unitIdentifier;
    private String programSystemCode;
    private String description;
    private String typeCode;
    private String typeCodeDescription;
    private String operatingStatusCodeDescription;
    private Short statusYear;
    private String unitOfMeasureCode;
    private String comments;
    private Double designCapacity;


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeCode() {
        return this.typeCode;
    }
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
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

    public String getUnitOfMeasureCode() {
        return this.unitOfMeasureCode;
    }
    public void setUnitOfMeasureCode(String unitOfMeasureCode) {
        this.unitOfMeasureCode = unitOfMeasureCode;
    }

    public Double getDesignCapacity() {
        return designCapacity;
    }
    public void setDesignCapacity(Double designCapacity) {
        this.designCapacity = designCapacity;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}