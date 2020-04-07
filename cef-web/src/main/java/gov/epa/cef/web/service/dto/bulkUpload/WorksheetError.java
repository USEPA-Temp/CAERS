package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class WorksheetError implements Serializable {

    private static final long serialVersionUID = 1L;

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
