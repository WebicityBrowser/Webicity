package com.github.webicitybrowser.webicitybrowser.engine.net.stream;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ByteChannelInputStream extends InputStream {

	private final ByteChannel byteChannel;
	private final ByteBuffer byteBuffer;
	
	private int available;

	public ByteChannelInputStream(ByteChannel byteChannel, int size) {
		this.byteChannel = byteChannel;
		this.byteBuffer = ByteBuffer.allocate(size);
	}

	@Override
	public int read() throws IOException {
		byte[] bytes = new byte[1];
		if (read(bytes, 0, 1) == -1) {
			return -1;
		};
		return bytes[0] & 0xFF;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		for (int read = 0; read < len;) {
			updateAvailable();
			
			if (available == -1) {
				// If we read anything before EOF, indicate what we read
				// Else, indicate the EOF
				return (read > 1) ? (read - 1) : -1;
			}
			
			int readSize = len - read;
			if (readSize > available) {
				readSize = available;
			}
			
			byteBuffer.get(b, off + read, readSize);
			read += readSize;
			available -= readSize;
		}
		return len;
	}

	@Override
	public int available() throws IOException {
		updateAvailable();
		return available;
	}
	
	@Override
	public void close() throws IOException {
		byteChannel.close();
	}
	
	private void updateAvailable() throws IOException {
		if (available != 0) {
			return;
		}
		
		byteBuffer.clear();
		available = byteChannel.read(byteBuffer);
		byteBuffer.flip();
	}
	
}
