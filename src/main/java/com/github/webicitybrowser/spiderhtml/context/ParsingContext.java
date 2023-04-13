package com.github.webicitybrowser.spiderhtml.context;

import java.io.PushbackReader;

import com.github.webicitybrowser.spiderhtml.token.Token;

public class ParsingContext {
	
	private final ReaderHandle readerHandle;
	
	private Token currentToken;

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

}
