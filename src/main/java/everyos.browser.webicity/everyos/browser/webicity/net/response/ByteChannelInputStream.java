package everyos.browser.webicity.net.response;

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
		if (read(bytes, 0, 1)==0) {
			throw new IOException("Not enough bytes were available!");
		};
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
				return i;
			} else if (available==0){
				if (i==0) {
					// This can break very easily, however, it is the best easy solution I could come up with at the time.
					// InputReaders tend to believe that the stream they wrap is blocking
					// Since it is not, we basically have to throw an exception, in hopes that we have enough data the next read attempt
					throw new IOException("Hackful compatibility until I have a better solution");
				}
				return i-1;
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
				available = byteChannel.read(byteBuffer);
			} catch (NeedsReadException e) {
				available = 0;
			}
			byteBuffer.flip();
		}
	}
}
