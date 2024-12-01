package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.DoctypeToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class DoctypeState implements TokenizeState {

	private final BeforeDoctypeNameState beforeDoctypeNameState;

	public DoctypeState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeDoctypeNameState = initializer.getTokenizeState(BeforeDoctypeNameState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeDoctypeNameState);
			break;
		case '>':
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(beforeDoctypeNameState);
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_DOCTYPE);
			// TODO: Force quirks
			context.emit(new DoctypeToken());
			context.emit(new EOFToken());
		}
	}

}
