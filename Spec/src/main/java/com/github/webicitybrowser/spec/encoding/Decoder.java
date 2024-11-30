package com.github.webicitybrowser.spec.encoding;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Decoder {

	private static final Map<byte[], Charset> BOMS = Map.of(
		new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }, StandardCharsets.UTF_8,
		new byte[] { (byte) 0xFE, (byte) 0xFF }, StandardCharsets.UTF_16BE,
		new byte[] { (byte) 0xFF, (byte) 0xFE }, StandardCharsets.UTF_16LE
	);

	private final ByteBuffer leftoverBuffer = ByteBuffer.allocate(10);

	private Charset encoding;
	private CharsetDecoder decoder;

	public int[] decode(byte[] buffer, Charset fallbackEncoding) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
		if (encoding == null) {
			this.encoding = bomSniff(byteBuffer);
			encoding = encoding != null ? encoding : fallbackEncoding;
			decoder = encoding.newDecoder();
		}
		encoding = encoding != null ? encoding : fallbackEncoding;

		leftoverBuffer.flip();
		ByteBuffer combinedBuffer = leftoverBuffer.hasRemaining() ?
			combineBuffers(leftoverBuffer, byteBuffer) : byteBuffer;
		leftoverBuffer.clear();

		CharBuffer charBuffer = CharBuffer.allocate(combinedBuffer.remaining());
		CoderResult result = decoder.decode(combinedBuffer, charBuffer, false);
		if (result.isUnderflow()) {
			leftoverBuffer.put(combinedBuffer);
		} else if (result.isError()) {
			throw new IOException("Error decoding input");
		}

		charBuffer.flip();

		int[] codepoints = new int[charBuffer.remaining()];
		int i = 0;

		while (charBuffer.hasRemaining()) {
			char currentChar = charBuffer.get();
        
			if (Character.isHighSurrogate(currentChar)) {
				if (!charBuffer.hasRemaining()) {
					leftoverBuffer.put((byte) currentChar);
					break;
				}
				char nextChar = charBuffer.get();
				if (Character.isLowSurrogate(nextChar)) {
					codepoints[i++] = Character.toCodePoint(currentChar, nextChar);
				} else {
					throw new IOException("Malformed surrogate pair");
				}
			} else if (Character.isLowSurrogate(currentChar)) {
				throw new IOException("Unexpected low surrogate");
			} else {
				codepoints[i++] = currentChar;
			}
		}

		int[] codepointsTrimmed = new int[i];
		System.arraycopy(codepoints, 0, codepointsTrimmed, 0, i);

		return codepointsTrimmed;
	}

	private Charset bomSniff(ByteBuffer buffer) throws IOException {
		outerLoop: for (Map.Entry<byte[], Charset> entry : BOMS.entrySet()) {
			byte[] bom = entry.getKey();
			if (buffer.remaining() < bom.length) {
				continue;
			}
			for (int i = 0; i < bom.length; i++) {
				if (buffer.get(i) != bom[i]) {
					continue outerLoop;
				}
			}

			buffer.position(bom.length);

			return entry.getValue();
		}
		
		return null;
	}

	private ByteBuffer combineBuffers(ByteBuffer buffer1, ByteBuffer buffer2) {
		ByteBuffer combinedBuffer = ByteBuffer.allocate(buffer1.remaining() + buffer2.remaining());
		combinedBuffer.put(buffer1);
		combinedBuffer.put(buffer2);
		combinedBuffer.flip();
		return combinedBuffer;
	}

}
