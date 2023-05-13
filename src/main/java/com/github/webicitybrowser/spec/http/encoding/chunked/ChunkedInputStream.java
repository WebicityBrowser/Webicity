package com.github.webicitybrowser.spec.http.encoding.chunked;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.http.util.ParseUtil;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public class ChunkedInputStream extends InputStream {

private final InputStream stream;
	
	private int remainingChunkLength = 0;
	private boolean ended = false;

	public ChunkedInputStream(InputStream stream) {
		this.stream = stream;
	}

	@Override
	public int read() throws IOException {
		byte[] bytes = new byte[1];
		if (read(bytes, 0, 1) == -1) {
			return -1;
		}
		return bytes[0] & 0xFF;
	}
	
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (remainingChunkLength == 0 && !ended) {
			readNextChunkLength();
		}
		
		if (ended) {
			return -1;
		};
		
		int read = stream.read(b, off, Math.min(len, remainingChunkLength));
		remainingChunkLength -= read;
		
		if (remainingChunkLength == 0) {
			ensureStreamNotClosed(stream.read());
			ensureStreamNotClosed(stream.read());
		}
		
		return read;
	}

	@Override
	public int available() throws IOException {
		if (ended) {
			return -1;
		}
		return stream.available();
	}
	
	@Override
	public void close() throws IOException {
		stream.close();
		super.close();
	}
	
	private void readNextChunkLength() throws IOException {
		int parsedChunkSize = 0;
		for (int ch = stream.read(); ch != '\r'; ch = stream.read()) {
			ensureStreamNotClosed(ch);
			if (!ASCIIUtil.isASCIIHexDigit(ch)) {
				ParseUtil.expect("a digit", ch);
			}
			parsedChunkSize = parsedChunkSize * 16 + ASCIIUtil.fromASCIIHexCharacter(ch);
		}
		ParseUtil.expect("CRLF (line feed)", '\n', stream.read());
		//TODO: Support chunk extensions
		remainingChunkLength = parsedChunkSize;
		if (parsedChunkSize == 0) {
			ended = true;
		}
	}

	private void ensureStreamNotClosed(int ch) throws IOException {
		if (ch == -1) {
			throw new IOException("Socket closed while reading chunks");
		}
	}

}
