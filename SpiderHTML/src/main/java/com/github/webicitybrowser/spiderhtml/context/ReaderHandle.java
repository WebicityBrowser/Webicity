package com.github.webicitybrowser.spiderhtml.context;

import java.io.IOException;
import java.io.PushbackReader;

public class ReaderHandle {
	
	private final PushbackReader reader;
	
	public ReaderHandle(PushbackReader reader) {
		this.reader = reader;
	}

	public void unread(int ch) throws IOException {
		if (ch != -1) {
			reader.unread(ch);
		}
	}
	
	public String lookahead(int num) throws IOException {
		int[] chars = new int[num];
		int totalLen = 0;
		for (int i = 0; i < num; i++) {
			chars[i] = reader.read();
			if (chars[i] != -1) {
				totalLen++;
			}
		}
		for (int i = num - 1; i >= 0; i--) {
			reader.unread(chars[i]);
		}
		return new String(chars, 0, totalLen);
	}
	
	public void eat(int num) throws IOException {
		for (int i = 0; i < num; i++) {
			reader.read();
		}
	}

	public char peek() throws IOException {
		int ch = reader.read();
		if (ch != -1) reader.unread(ch);
		return (char) ch;
	}
	
}
