package gov.epa.cef.web.service.dto.bulkUpload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWorksheetDto {

    private final String sheetName;

    @JsonProperty("_row")
    private int row;

    public BaseWorksheetDto(WorksheetName sheetName) {

        this.sheetName = sheetName.sheetName();
    }

    public int getRow() {

        return row;
    }

    public void setRow(int row) {

        this.row = row;
    }

    public String getSheetName() {

        return sheetName;
    }
}
