package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class VersionBulkUploadDto extends BaseWorksheetDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String version;

    public VersionBulkUploadDto() {

        super(WorksheetName.Version);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
