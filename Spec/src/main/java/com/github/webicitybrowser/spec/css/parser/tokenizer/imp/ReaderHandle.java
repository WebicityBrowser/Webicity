package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;
import java.io.PushbackReader;

public class ReaderHandle {
	
	private final PushbackReader reader;

	public ReaderHandle(PushbackReader reader) {
		this.reader = reader;
	}

	public int read() throws IOException {
		int ch = reader.read();
		switch(ch) {
		case '\r':
			{
				int ch2 = reader.read();
				if (ch2 != '\n') {
					unread(ch2);
				}
			}
			return '\n';
		case '\f':
			return '\n';
		default:
			return ch;
		}
	}
	
	public int peek() throws IOException {
		int result = read();
		unread(result);
		
		return result;
	}

	public void unread(int ch) throws IOException {
		if (ch != -1) {
			reader.unread(ch);
		}
	}
	
}
