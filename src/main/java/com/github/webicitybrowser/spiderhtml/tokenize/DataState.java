package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class DataState implements TokenizeState {

	private final TagOpenState tagOpenState;

	public DataState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.tagOpenState = initializer.getTokenizeState(TagOpenState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		case '<':
			context.setTokenizeState(tagOpenState);
			break;
		case -1:
			context.emit(new EOFToken());
			break;
		default:
			// TODO
			throw new UnsupportedOperationException();
		}
	}
	
}