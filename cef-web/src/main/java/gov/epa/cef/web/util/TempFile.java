package gov.epa.cef.web.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.function.Consumer;

public class TempFile implements AutoCloseable {

    private final File file;

    private final String fileName;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private TempFile(@NotNull File file, @NotNull String fileName) {

        super();

        Preconditions.checkNotNull(file, "File can not be null.");

        this.file = file;
        this.fileName = fileName;
    }

    public static TempFile create(@NotNull String fileName) {

        return create(fileName, ".tmp");
    }

    public static TempFile create(@NotNull String fileName, @NotNull String suffix) {

        File file = null;

        try {

            file = File.createTempFile(fileName, suffix);

        } catch (IOException e) {

            throw new IllegalStateException(e);
        }

        return new TempFile(file, fileName);
    }

    public static TempFile from(@NotNull InputStream inputStream, String fileName) {

        File file = null;

        try {

            file = File.createTempFile(fileName, ".tmp");
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {

            if (file != null) {
                new FileCloser().accept(file);
            }

            throw new IllegalStateException(e);
        }

        return new TempFile(file, fileName);
    }

    public static TempFile from(@NotNull File file, String fileName) {

        return new TempFile(file, MoreObjects.firstNonNull(Strings.emptyToNull(fileName), file.getName()));
    }

    @Override
    public void close() {

        new FileCloser().accept(file);
    }

    public File getFile() {

        return file;
    }

    public String getFileName() {

        return fileName;
    }

    public Path toPath() {

        return this.file.toPath();
    }

    private static class FileCloser implements Consumer<File> {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void accept(File file) {

            if (file.exists()) {

                if (file.delete() == false) {

                    logger.warn("Unable to delete file {}, will attempt to deleteOnExit.", file.getAbsolutePath());
                    file.deleteOnExit();
                }
            }
        }
    }
}
