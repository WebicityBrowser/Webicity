package com.github.webicitybrowser.spec.encoding;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EncodingUtil {

	private static final Map<byte[], Charset> BOMS = Map.of(
		new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF }, StandardCharsets.UTF_8,
		new byte[] { (byte) 0xFE, (byte) 0xFF }, StandardCharsets.UTF_16BE,
		new byte[] { (byte) 0xFF, (byte) 0xFE }, StandardCharsets.UTF_16LE
	);

	public static Reader decode(InputStream inputStream, Charset fallbackEncoding) throws IOException {
		PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream, 3);
		Charset encoding = bomSniff(pushbackInputStream);
		encoding = encoding != null ? encoding : fallbackEncoding;

		return new InputStreamReader(pushbackInputStream, encoding);
	}

	private static Charset bomSniff(PushbackInputStream inputStream) throws IOException {
		outerLoop: for (Map.Entry<byte[], Charset> entry : BOMS.entrySet()) {
			byte[] bom = entry.getKey();
			byte[] buffer = new byte[bom.length];
			int totalRead = inputStream.read(buffer);
			if (totalRead == -1) return null;
			if (totalRead != buffer.length) {
				inputStream.unread(buffer, 0, totalRead);
				continue;
			}
			for (int i = 0; i < buffer.length; i++) {
				if (buffer[i] != bom[i]) {
					inputStream.unread(buffer, 0, totalRead);
					continue outerLoop;
				}
			}

			return entry.getValue();
		}
		
		return null;
	}

}
