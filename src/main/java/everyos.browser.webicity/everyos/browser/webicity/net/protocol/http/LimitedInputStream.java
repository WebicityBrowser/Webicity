package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;

public class LimitedInputStream extends InputStream {
	
	private final InputStream stream;
	
	private int size;

	public LimitedInputStream(InputStream stream, int size) {
		this.stream = stream;
		this.size = size;
	}

	@Override
	public int read() throws IOException {
		if (size<=0) return -1;
		int result = stream.read();
		size--;
		return result;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (size<=0) {
			b[off] = -1;
			return -1;
		}
		
		if (len>size) {
			len = size;
		}
		
		int read = stream.read(b, off, len);
		if (read==-1) return -1;
		size-=len;
		return read;
	}
	
	@Override
	public int available() throws IOException {
		if (size<=0) return -1;
		int available = stream.available();
		if (available>size) return size;
		return available;
	}
}
