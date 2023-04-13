package com.github.webicitybrowser.spiderhtml.context;

import java.io.IOException;
import java.io.PushbackReader;

public class ReaderHandle {
	
	private final PushbackReader reader;
	
	public ReaderHandle(PushbackReader reader) {
		this.reader = reader;
	}

	public void unread(int ch) throws IOException {
		reader.unread(ch);
	}
	
	public String lookahead(int num) throws IOException {
		int[] chars = new int[num];
		for (int i = 0; i < num; i++) {
			chars[i] = reader.read();
		}
		for (int i = num - 1; i >= 0; i--) {
			if (chars[i] != -1) {
				reader.unread(chars[i]);
			}
		}
		return new String(chars, 0, num);
	}
	
	public void eat(int num) throws IOException {
		for (int i = 0; i < num; i++) {
			reader.read();
		}
	}
	
}
