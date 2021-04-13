package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.jinfra.IOPendingException;

public class TimeoutInputStream extends InputStream {
	private InputStream stream;
	private int timeout;
	private long lastSuccess;

	public TimeoutInputStream(InputStream stream, int timeout) {
		this.stream = stream;
		this.timeout = timeout;
		this.lastSuccess = System.currentTimeMillis();
	}

	@Override
	public int read() throws IOException {
		//System.out.println("Attempt");
		try {
			int res = stream.read();
			//System.out.println("Attempt4");
			this.lastSuccess = System.currentTimeMillis();
			return res;
		} catch (IOPendingException e) {
			//System.out.println("Attempt3");
			throw computeException(e);
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		//System.out.println("Attempt2");
		try {
			int res = stream.read(b, off, len);
			this.lastSuccess = System.currentTimeMillis();
			return res;
		} catch (IOPendingException e) {
			throw computeException(e);
		}
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
	}
	
	@Override
	public int available() throws IOException {
		return stream.available();
	}
	
	private IOException computeException(IOPendingException e) {
		if (System.currentTimeMillis()-this.lastSuccess>=timeout) {
			return new IOException("Socket timed out");
		}
		return e;
	}
}
