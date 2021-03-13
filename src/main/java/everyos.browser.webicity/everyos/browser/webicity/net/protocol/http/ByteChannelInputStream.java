package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

import tlschannel.NeedsReadException;

public class ByteChannelInputStream extends InputStream {
	private int available;
	private ByteChannel byteChannel;
	private ByteBuffer byteBuffer;

	public ByteChannelInputStream(ByteChannel byteChannel, int size) {
		this.byteChannel = byteChannel;
		this.byteBuffer = ByteBuffer.allocate(size);
	}

	@Override
	public int read() throws IOException {
		if (available==-1) return -1;
		byte[] bytes = new byte[1];
		read(bytes, 0, 1);
		return bytes[0];
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (available==-1) return -1;
		
		int i=0;
		while (i<len) {
			if (available==0) {
				updateAvailable();
			}
			if (available==-1) {
				b[i] = -1;
				if (i>1) return i-1;
				return i;
			} else if (available==0){
				if (i==0) {
					// This can break very easily, however, it is the best easy solution I could come up with at the time.
					// InputReaders tend to believe that the stream they wrap is blocking
					// Since it is not, we basically have to throw an exception, in hopes that we have enough data the next read attempt
					throw new IOPendingException();
				}
				return i;
			}
			
			int flen = len-i;
			if (flen>available) flen = available;
			byteBuffer.get(b, off+i, flen);
			i+=flen;
			available-=flen;
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
		if (available==0) {
			byteBuffer.clear();
			try {
				//System.out.println("A");
				available = byteChannel.read(byteBuffer);
				//System.out.println("B");
			} catch (NeedsReadException e) {
				available = 0;
			}
			byteBuffer.flip();
		}
	}
}
