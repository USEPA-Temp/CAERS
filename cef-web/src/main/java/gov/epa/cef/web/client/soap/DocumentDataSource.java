package gov.epa.cef.web.client.soap;

import javax.activation.FileDataSource;
import javax.activation.FileTypeMap;
import java.io.File;

public class DocumentDataSource extends FileDataSource {

    public DocumentDataSource(File file, String contentType) {

        super(file);
        setFileTypeMap(new StaticFileTypeMap(contentType));
    }

    private static class StaticFileTypeMap extends FileTypeMap {

        private final String contentType;

        StaticFileTypeMap(String contentType) {

            this.contentType = contentType;
        }

        @Override
        public String getContentType(File file) {

            return contentType;
        }

        @Override
        public String getContentType(String filename) {

            return contentType;
        }
    }
}
