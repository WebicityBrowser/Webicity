package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class RawTextState implements TokenizeState {
	
	private final RawTextLessThanSignState rawTextLessThanSignState;

	public RawTextState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.rawTextLessThanSignState = initializer.getTokenizeState(RawTextLessThanSignState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO
		System.out.println("Reached!");
		switch (ch) {
		case '<':
			context.setTokenizeState(rawTextLessThanSignState);
			break;
		default:
			context.emit(new CharacterToken(ch));
		}
	}

}
