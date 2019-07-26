package gov.epa.cef.web.soap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataSource;
import java.io.*;

/**
 * @author dfladung
 */
public class DocumentDataSource implements DataSource {

    private static final Logger logger = LoggerFactory.getLogger(DocumentDataSource.class);

    protected File file;
    protected String contentType;


    public DocumentDataSource(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }


    public InputStream getInputStream() throws IOException {
        return new FileInputStream(this.file);
    }


    public String getContentType() {
        return this.contentType;
    }


    public String getName() {
        return this.file.getName();
    }

    public OutputStream getOutputStream() throws IOException {
        return new FileOutputStream(this.file);
    }


    public File getFile() {
        return file;
    }


    protected void finalize() throws Throwable {
        try {
            super.finalize();
        } catch (Throwable t) {
            throw t;
        } finally {
            if (file.exists()) {
                try {
                    FileUtils.forceDelete(file);
                } catch (IOException e) {
                    logger.warn(e.getMessage());
                }
            }
        }
    }
}