package com.github.webicitybrowser.spec.fetch.decoder;

import java.nio.ByteBuffer;

public class GzipDecoder extends DeflateDecoder {

	private boolean headerSkipped = false;

	@Override
	public byte[] translate(byte[] data) {
		ByteBuffer buffer = ByteBuffer.wrap(data);
		if (!headerSkipped) {
			if (!skipGZIPHeader(buffer)) {
				appendRemaining(buffer);
				return new byte[0];
			}
			headerSkipped = true;
		}
		
		return deflate(buffer);
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

		return skipExtraFields(buffer);
	}

	private boolean skipExtraFields(ByteBuffer buffer) {
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

}
