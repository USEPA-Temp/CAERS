package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class ControlPathBulkUploadDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long facilitySiteId;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacilitySiteId() {
        return facilitySiteId;
    }

    public void setFacilitySiteId(Long facilitySiteId) {
        this.facilitySiteId = facilitySiteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
