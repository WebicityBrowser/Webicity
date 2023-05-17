package com.github.webicitybrowser.spiderhtml.context;

import java.io.PushbackReader;

import com.github.webicitybrowser.spiderhtml.token.Token;

public class ParsingContext {
	
	private final StringBuilder temporaryBuffer = new StringBuilder();
	
	private final ReaderHandle readerHandle;
	
	private Token currentToken;
	private int charRefCode;

	public ParsingContext(PushbackReader reader) {
		this.readerHandle = new ReaderHandle(reader);
	}

	public ReaderHandle readerHandle() {
		return this.readerHandle;
	}

	public void setCurrentToken(Token token) {
		this.currentToken = token;
	}

	@SuppressWarnings("unchecked")
	public <T extends Token> T getCurrentToken(Class<T> tokenCls) {
		return (T) this.currentToken;
	}
	
	public void resetTemporaryBuffer() {
		temporaryBuffer.setLength(0);
	}

	public void appendToTemporaryBuffer(int ch) {
		temporaryBuffer.appendCodePoint(ch);
	}

	public String getTemporaryBuffer() {
		return temporaryBuffer.toString();
	}

	public void setCharacterReferenceCode(int code) {
		this.charRefCode = code;
	}
	
	public int getCharacterReferenceCode() {
		return this.charRefCode;
	}

}
