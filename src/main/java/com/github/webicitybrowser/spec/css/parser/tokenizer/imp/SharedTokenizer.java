package com.github.webicitybrowser.spec.css.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public final class SharedTokenizer {
	
	private static final int MAX_CODE_POINT = 0x10FFFF;

	private SharedTokenizer() {}
	
	public static int consumeAnEscapedCodePoint(ReaderHandle reader) throws IOException {
		int ch = reader.read();
		if (ASCIIUtil.isASCIIHexDigit(ch)) {
			reader.unread(ch);
			return consumeHexEscapedCodePoint(reader);
		} else if (ch == -1) {
			return '\uFFFD';
		} else {
			return ch;
		}
	}
	
	public static boolean wouldStartValidEscapeSequence(ReaderHandle reader) throws IOException {
		int ch1 = reader.read();
		int ch2 = reader.peek();
		reader.unread(ch1);
		
		return
			ch1 == '\\' &&
			ch2 != '\n';
	}
	
	public static boolean isValidEscapeSequence(int ch1, ReaderHandle reader) throws IOException {
		int ch2 = reader.peek();
		
		return
			ch1 == '\\' &&
			ch2 != '\n';
	}
	
	public static DelimToken createDelimToken(int ch) {
		return () -> ch;
	}
	
	public static boolean isWhitespace(int ch) {
		return
			ch == '\n' ||
			ch == '\t' ||
			ch == ' ';
	}
	
	private static int consumeHexEscapedCodePoint(ReaderHandle reader) throws IOException {
		int total = 0;
		for (int i = 0; i < 6; i++) {
			int ch = reader.read();
			if (!ASCIIUtil.isASCIIHexDigit(ch)) {
				reader.unread(ch);
				break;
			}
			total <<= 4;
			total += ASCIIUtil.fromASCIIHexCharacter(ch);
		}
		int ch = reader.read();
		if (!isWhitespace(ch)) {
			reader.unread(ch);
		}
		
		if (total == 0 || total > MAX_CODE_POINT || isSurrogate(total)) {
			return '\uFFFD';
		}
		return total;
	}
	
	private static boolean isSurrogate(int total) {
		return (total >= '\uD800' && total <= '\uDFFF');
	}
	
}
