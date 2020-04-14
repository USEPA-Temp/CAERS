package gov.epa.cef.web.service.dto.bulkUpload;

import java.io.Serializable;

public class WorksheetError implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;

    private final int row;

    private final boolean systemError;

    private final String worksheet;

    private WorksheetError(String worksheet, int row, String message, boolean systemError) {

        this.row = row;
        this.worksheet = worksheet;
        this.message = message;
        this.systemError = systemError;
    }

    public WorksheetError(String worksheet, int row, String message) {

        this(worksheet, row, message, false);
    }

    public static WorksheetError createSystemError(String message) {

        return new WorksheetError("*", -1, message, true);
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

    public boolean isSystemError() {

        return systemError;
    }
}
