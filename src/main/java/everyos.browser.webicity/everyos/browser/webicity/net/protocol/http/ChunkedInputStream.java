package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;

public class ChunkedInputStream extends InputStream {
	
	private final InputStream stream;
	
	private int remainingChunkLength = 0;
	private boolean ended = false;

	public ChunkedInputStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		byte[] bytes = new byte[1];
		if (read(bytes, 0, 1) == -1) {
			return -1;
		}
		return bytes[0] & 0xFF;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (ended) {
			return -1;
		};
		
		if (remainingChunkLength == 0) {
			int parsedChunkSize = 0;
			for (int chi = stream.read(); chi != '\r'; chi = stream.read()) {
				if (chi == -1) {
					ended = true;
					return -1;
				}
				parsedChunkSize = parsedChunkSize * 16 + Character.digit(chi, 16);
			}
			stream.read();
			//TODO: Support chunk extensions
			if (parsedChunkSize == 0) {
				ended = true;
				return -1;
			}
			remainingChunkLength = parsedChunkSize + 2;
		}
		
		int read = stream.read(b, off, Math.min(len, remainingChunkLength));
		remainingChunkLength -= read;
		
		return read; //TODO
	}

	@Override
	public int available() throws IOException {
		if (ended) {
			return -1;
		}
		return stream.available();
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
		super.close();
	}
	
}
