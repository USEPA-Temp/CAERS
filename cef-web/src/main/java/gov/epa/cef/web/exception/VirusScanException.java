package gov.epa.cef.web.exception;

import gov.epa.cef.web.util.TempFile;

public class VirusScanException extends ApplicationException {

    private final String filename;

    public VirusScanException(TempFile file, String errorMessage) {

        super(ApplicationErrorCode.E_VALIDATION, errorMessage);
        this.filename = file.getFileName();
    }

    public String getFilename() {

        return this.filename;
    }
}
