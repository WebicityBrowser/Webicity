package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token.TokenMeta;

public class TokenizerStream {
	
	private final PushbackReader reader;

	private int line = 1;
	private int column = 1;

	private int lastChar;

	public TokenizerStream(Reader inputReader) {
		this.reader = new PushbackReader(inputReader, 8);	
	}

	public TokenMeta meta() {
		return new TokenMeta(line, column);
	}

	public int read() throws IOException {
		if (this.lastChar == -1) return -1;
		this.lastChar = reader.read();
		if (lastChar == '\n' && peek() == '\r') {
			reader.read();
		}
		updatePosition();

		return this.lastChar;
	}

	public String read(int length) throws IOException {
		if (this.lastChar == -1) return null;
		char[] buffer = new char[length];
		int read = reader.read(buffer);
		if (read == -1) {
			return null;
		}
		this.lastChar = buffer[read - 1];
		updatePosition();
		return new String(buffer, 0, read);
	}

	public void unread() throws IOException {
		if (this.lastChar == -2) {
			throw new IllegalStateException("Cannot unread: already unread");
		}
		if (this.lastChar != -1) {
			reader.unread(this.lastChar);
		}
		if (this.lastChar == '\n') {
			line--;
			// TODO: properly calculate column
		} else {
			column--;
		}
	
		this.lastChar = -2;
	}

	public int peek() throws IOException {
		if (this.lastChar == -1) return -1;
		int nextChar = reader.read();
		if (nextChar == -1) {
			return -1;
		}
		reader.unread(nextChar);
		return nextChar;
	}

	public String peek(int length) throws IOException {
		if (this.lastChar == -1) return null;
		char[] buffer = new char[length];
		int read = reader.read(buffer);
		if (read == -1) {
			return null;
		}
		reader.unread(buffer, 0, read);
		return new String(buffer, 0, read);
	}

	private void updatePosition() {
		if (this.lastChar == '\n') {
			this.line++;
			this.column = 1;
		} else {
			this.column++;
		}
	}

}
