package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;

public class ChunkedInputStream extends InputStream {
	private int next = 0;
	private boolean ended = false;
	private InputStream stream;

	public ChunkedInputStream(InputStream stream) {
		this.stream = stream;
		// TODO: This has race conditions when used with NIO sockets.
	}

	@Override
	public int read() throws IOException {
		if (ended) return -1;
		
		byte[] bytes = new byte[1];
		if (read(bytes, 0, 1)==0) {
			throw new IOException("Not enough bytes were available!");
		}
		return bytes[0];
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (ended) {
			b[0] = -1;
			return -1;
		};
		if (next==0) {
			StringBuilder size = new StringBuilder(4);
			int chi;
			for (;;) {
				chi = stream.read();
				if (chi=='\r') {
					break;
				}
				if (chi==-1) {
					ended = true;
					break;
				}
				size.append((char) chi);
			}
			stream.read();
			//TODO: Support chunk extensions
			if (size.length()==0||Integer.parseInt(size.toString(), 16)==0) {
				ended = true;
				b[0] = -1;
				return -1;
			}
			next = Integer.parseInt(size.toString(), 16)+2;
		}
		
		if (len > next) {
			len = next;
		}
		int read = stream.read(b, off, len);
		next-=read;
		
		return read; //TODO
	}

	@Override
	public int available() throws IOException {
		if (ended) return -1;
		return stream.available();
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
		super.close();
	}
}
