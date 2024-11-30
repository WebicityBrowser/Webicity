package com.github.webicitybrowser.spec.fetch.decoder;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.github.webicitybrowser.spec.fetch.FetchDecoder;

public class DeflateDecoder implements FetchDecoder {

	private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);

	private final Inflater inflater = new Inflater(true);

	private ByteBuffer remainingData = EMPTY_BUFFER;

    @Override
	public byte[] translate(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		try {
			appendRemaining(buffer);
			buffer = remainingData;
			remainingData = EMPTY_BUFFER;
			
			inflater.setInput(buffer.array(), buffer.position(), buffer.remaining());
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			while (!inflater.finished()) {
				int count = inflater.inflate(buf);
				out.write(buf, 0, count);
			}

			if (inflater.needsDictionary()) {
				throw new RuntimeException("Dictionary needed during decompression");
			}

			if (inflater.getRemaining() > 0) {
				remainingData = ByteBuffer.allocate(inflater.getRemaining());
				remainingData.put(buffer.array(), buffer.position() + buffer.remaining() - inflater.getRemaining(), inflater.getRemaining());
			}

			return out.toByteArray();
		} catch (DataFormatException e) {
			throw new RuntimeException("Data format error during decompression", e);
		}
	}

	@Override
	public void close() {
		inflater.end();
	}

	private void appendRemaining(ByteBuffer buffer) {
		ByteBuffer newBuffer = ByteBuffer.allocate(remainingData.remaining() + buffer.remaining());
		newBuffer.put(remainingData);
		newBuffer.put(buffer);
		newBuffer.flip();
		remainingData = newBuffer;
	}

}
