package gov.epa.cef.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

//borrowed from PassthroughBlob
public class TempFileBlob implements Blob, AutoCloseable {

	private InputStream binaryStream;

	private long contentLength;

	private boolean freed = false;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public TempFileBlob(TempFile tempFile) throws FileNotFoundException {

		this.binaryStream = new FileInputStream(tempFile.getFile());
		this.contentLength = tempFile.length();
	}

	@Override
	public void close() {

		free();
	}

	public void free() {

		logger.debug("Freeing blob resources.");

		if (this.binaryStream != null) {

			try {
				this.binaryStream.close();
			} catch (IOException e) {
				logger.warn("Unable to close binary stream", e);
			}

			this.binaryStream = null;
		}

		this.freed = true;
	}

	public InputStream getBinaryStream() throws SQLException {

		if (this.freed) {

			throw new SQLException("Blob is invalid; free() was called.");
		}

		return this.binaryStream;
	}

	public InputStream getBinaryStream(long pos, long length)
			throws SQLException {

		throw new UnsupportedOperationException();
	}

	public byte[] getBytes(long pos, int length) throws SQLException {

		throw new UnsupportedOperationException();
	}

	public long length() throws SQLException {

		return this.contentLength;
	}

	public long position(byte[] pattern, long start) throws SQLException {

		throw new UnsupportedOperationException();
	}

	public long position(Blob pattern, long start) throws SQLException {

		throw new UnsupportedOperationException();
	}

	public OutputStream setBinaryStream(long pos) throws SQLException {

		throw new UnsupportedOperationException();
	}

	public int setBytes(long pos, byte[] bytes) throws SQLException {

		throw new UnsupportedOperationException();
	}

	public int setBytes(long pos, byte[] bytes, int offset, int len)
			throws SQLException {

		throw new UnsupportedOperationException();
	}

	public void truncate(long len) throws SQLException {

		throw new UnsupportedOperationException();
	}
}
