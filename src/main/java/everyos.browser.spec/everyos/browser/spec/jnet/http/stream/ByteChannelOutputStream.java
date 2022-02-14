package everyos.browser.spec.jnet.http.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ByteChannelOutputStream extends OutputStream {
	private final ByteChannel byteChannel;

	public ByteChannelOutputStream(ByteChannel byteChannel) {
		this.byteChannel = byteChannel;
	}

	@Override
	public void write(int b) throws IOException {
		write(new byte[] {(byte) b});
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		byteChannel.write(ByteBuffer.wrap(b));
	}
}
