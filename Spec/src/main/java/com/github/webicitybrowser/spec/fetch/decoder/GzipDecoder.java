package com.github.webicitybrowser.spec.fetch.decoder;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.github.webicitybrowser.spec.fetch.FetchDecoder;

public class GzipDecoder implements FetchDecoder {

	private static final ByteBuffer EMPTY_BUFFER = ByteBuffer.allocate(0);

	private final Inflater inflater = new Inflater(true);

	private ByteBuffer remainingData = EMPTY_BUFFER;
	private boolean headerSkipped = false;

    @Override
	public byte[] translate(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		try {
			if (!headerSkipped) {
				if (!skipGZIPHeader(buffer)) {
					appendRemaining(buffer);
					return new byte[0];
				}
				headerSkipped = true;
			}
		
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

	private boolean skipGZIPHeader(ByteBuffer buffer) {
		if (buffer.remaining() < 10) {
			return false;
		}

		if (buffer.get(0) != (byte) 0x1F || buffer.get(1) != (byte) 0x8B) {
			throw new IllegalArgumentException("Invalid GZIP header");
		}

		if (buffer.get(2) != (byte) 0x08) {
			throw new IllegalArgumentException("Unsupported compression method");
		}

		// Ensure we have enough data to skip extra fields
		int offset = 10;
		byte headerTag = buffer.get(3);
		if ((headerTag & 0x04) != 0) { // FEXTRA
			if (buffer.remaining() < offset + 2) {
				return false;
			}
			offset += 2 + buffer.getShort(offset);
		}
		if ((headerTag & 0x08) != 0) { // FNAME
			offset = scanZeroTerminator(buffer, offset);
			if (offset == -1) return false;
		}
		if ((headerTag & 0x10) != 0) { // FCOMMENT
			offset = scanZeroTerminator(buffer, offset);
			if (offset == -1) return false;
		}
		if ((headerTag & 0x02) != 0) { // FHCRC
			if (buffer.remaining() < offset + 2) {
				return false;
			}
			offset += 2;
		}
		
		buffer.position(offset);
		return true;
	}

	private int scanZeroTerminator(ByteBuffer buffer, int offset) {
		for (int i = offset; i < buffer.remaining(); i++) {
			if (buffer.get(i) == 0) {
				return i + 1;
			}
		}
		return -1;
	}

	private void appendRemaining(ByteBuffer buffer) {
		ByteBuffer newBuffer = ByteBuffer.allocate(remainingData.remaining() + buffer.remaining());
		newBuffer.put(remainingData);
		newBuffer.put(buffer);
		newBuffer.flip();
		remainingData = newBuffer;
	}

}
