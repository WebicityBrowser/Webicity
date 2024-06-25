package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class RawTextLessThanSignState implements TokenizeState {

	private final RawTextEndTagOpenState rawTextEndTagOpenState;
	private final RawTextState rawTextState;

	public RawTextLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.rawTextEndTagOpenState = initializer.getTokenizeState(RawTextEndTagOpenState.class);
		this.rawTextState = initializer.getTokenizeState(RawTextState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '/':
			parsingContext.resetTemporaryBuffer();
			context.setTokenizeState(rawTextEndTagOpenState);
			break;
		default:
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(rawTextState);
		}
	}

}
