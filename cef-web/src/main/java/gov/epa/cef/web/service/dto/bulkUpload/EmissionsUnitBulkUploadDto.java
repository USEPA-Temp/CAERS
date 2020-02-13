package gov.epa.cef.web.service.dto.bulkUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

public class EmissionsUnitBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Emissions Unit ID is required.")
    private Long id;

    @NotNull(message = "Facility Site ID is required.")
    private Long facilitySiteId;

    @NotBlank(message = "Unit Identifier is required.")
    @Size(max = 20, message = "Unit Identifier can not exceed {max} chars; found '${validatedValue}'.")
    private String unitIdentifier;

    @Size(max = 20, message = "Program System Code can not exceed {max} chars; found '${validatedValue}'.")
    private String programSystemCode;

    @Size(max = 100, message = "Description can not exceed {max} chars; found '${validatedValue}'.")
    private String description;

    @NotBlank(message = "Type Code is required.")
    @Size(max = 20, message = "Type Code can not exceed {max} chars; found '${validatedValue}'.")
    private String typeCode;

    @NotBlank(message = "Operating Status Code is required.")
    @Size(max = 20, message = "Operating Status Code can not exceed {max} chars; found '${validatedValue}'.")
    private String operatingStatusCodeDescription;

    private Short statusYear;

    @Size(max = 20, message = "Unit of Measure Code can not exceed {max} chars; found '${validatedValue}'.")
    private String unitOfMeasureCode;

    @Size(max = 400, message = "Comments can not exceed {max} chars; found '${validatedValue}'.")
    private String comments;

    private BigDecimal designCapacity;

    public EmissionsUnitBulkUploadDto() {

        super(WorksheetName.EmissionsUnit);
    }

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

    public BigDecimal getDesignCapacity() {
        return designCapacity;
    }
    public void setDesignCapacity(BigDecimal designCapacity) {
        this.designCapacity = designCapacity;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
