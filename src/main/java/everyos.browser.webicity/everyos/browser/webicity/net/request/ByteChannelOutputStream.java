package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;

public class ByteChannelOutputStream extends OutputStream {
	private ByteChannel byteChannel;

	public ByteChannelOutputStream(ByteChannel byteChannel) {
		this.byteChannel = byteChannel;
	}

	@Override
	public void write(int b) throws IOException {
		byteChannel.write(ByteBuffer.wrap(new byte[] {(byte) b}));
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		byteChannel.write(ByteBuffer.wrap(b));
	}
}
