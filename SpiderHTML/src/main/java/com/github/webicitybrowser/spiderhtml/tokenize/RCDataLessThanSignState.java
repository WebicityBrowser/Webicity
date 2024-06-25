package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class RCDataLessThanSignState implements TokenizeState {

	private final RCDataEndTagOpenState rcDataEndTagOpenState;
	private final RCDataState rcDataState;

	public RCDataLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.rcDataEndTagOpenState = initializer.getTokenizeState(RCDataEndTagOpenState.class);
		this.rcDataState = initializer.getTokenizeState(RCDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '/':
			parsingContext.resetTemporaryBuffer();
			context.setTokenizeState(rcDataEndTagOpenState);
			break;
		default:
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(rcDataState);
		}
	}

}
