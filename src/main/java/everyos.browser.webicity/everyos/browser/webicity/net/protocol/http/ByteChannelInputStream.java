package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

import everyos.browser.webicity.concurrency.jroutine.JRoutine;
import tlschannel.NeedsReadException;

public class ByteChannelInputStream extends InputStream {
	private final ByteChannel byteChannel;
	private final ByteBuffer byteBuffer;
	private final int timeout;
	
	private int available;

	public ByteChannelInputStream(ByteChannel byteChannel, int size, int timeout) {
		this.byteChannel = byteChannel;
		this.byteBuffer = ByteBuffer.allocate(size);
		this.timeout = timeout;
	}

	@Override
	public int read() throws IOException {
		if (available == -1) {
			return -1;
		}
		
		byte[] bytes = new byte[1];
		read(bytes, 0, 1);
		return bytes[0] & 0xFF;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (available == -1) {
			return -1;
		}
		
		for (int i = 0; i < len;) {
			long lastSuccess = System.currentTimeMillis();
			while (available == 0) {
				updateAvailable();
				if (available == 0) {
					throwExceptionIfTimedOut(lastSuccess);
					JRoutine.getJRoutine().yield();
				}
			}
			
			if (available==-1) {
				b[i] = -1;
				if (i > 1) {
					return i-1;
				}
				return i;
			}
			
			int flen = len-i;
			if (flen > available) {
				flen = available;
			}
			byteBuffer.get(b, off+i, flen);
			i += flen;
			available -= flen;
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
		if (available == 0) {
			byteBuffer.clear();
			try {
				available = byteChannel.read(byteBuffer);
			} catch (NeedsReadException e) {
				available = 0;
			}
			byteBuffer.flip();
		}
	}
	
	private void throwExceptionIfTimedOut(long lastSuccess) throws IOException {
		if (System.currentTimeMillis()-lastSuccess >= timeout) {
			throw new IOException("Socket timed out");
		}
	}
}
