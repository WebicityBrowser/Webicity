package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AfterAttributeValueQuotedState implements TokenizeState {

	private final BeforeAttributeNameState beforeAttributeNameState;
	private final DataState dataState;

	public AfterAttributeValueQuotedState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeAttributeNameState);
			break;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(StartTagToken.class));
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

}
