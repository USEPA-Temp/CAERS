package gov.epa.cef.web.service.dto.bulkUpload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseWorksheetDto {

    private final String sheetName;

    @JsonProperty("_row")
    private int row;

    static final String PositiveDecimalPattern = "^\\d*(\\.\\d+)?$";

    static final String LatitudePattern = "^[+-]?\\d{0,2}(\\.\\d{1,6})?$";

    static final String LongitudePattern = "^[+-]?\\d{0,3}(\\.\\d{1,6})?$";

    static final String PercentPattern = "^\\d{0,3}(\\.\\d)?$";

    static final String PositiveIntPattern = "^\\d{0,10}$";

    static final String YearPattern = "^\\d{0,4}$";

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
