package gov.epa.cef.web.service.dto.bulkUpload;

public class WorksheetError {

    private final String message;

    private final int row;

    private final String worksheet;

    public WorksheetError(String worksheet, int row, String message) {

        this.row = row;
        this.worksheet = worksheet;
        this.message = message;
    }

    public String getMessage() {

        return message;
    }

    public int getRow() {

        return row;
    }

    public String getWorksheet() {

        return worksheet;
    }
}
