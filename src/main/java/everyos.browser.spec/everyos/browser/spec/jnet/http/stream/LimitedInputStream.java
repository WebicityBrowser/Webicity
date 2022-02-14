package everyos.browser.spec.jnet.http.stream;

import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends InputStream {
	
	private final InputStream stream;
	
	private int remainingBytes;

	public LimitedInputStream(InputStream stream, int size) {
		this.stream = stream;
		this.remainingBytes = size;
	}

	@Override
	public int read() throws IOException {
		if (remainingBytes <= 0) {
			return -1;
		}
		int result = stream.read();
		remainingBytes--;
		return result;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (remainingBytes <= 0) {
			return -1;
		}
		
		if (len > remainingBytes) {
			len = remainingBytes;
		}
		
		int read = stream.read(b, off, len);
		if (read == -1) {
			remainingBytes = 0;
			return -1;
		}
		remainingBytes -= len;
		return read;
	}
	
	@Override
	public int available() throws IOException {
		if (remainingBytes <= 0) {
			return -1;
		}
		int available = stream.available();
		if (available > remainingBytes) {
			return remainingBytes;
		}
		return available;
	}
}
