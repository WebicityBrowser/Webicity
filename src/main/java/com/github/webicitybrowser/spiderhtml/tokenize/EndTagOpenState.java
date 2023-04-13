package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;

public class EndTagOpenState implements TokenizeState {
	
	private TagNameState tagNameState;

	public EndTagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.tagNameState = initializer.getTokenizeState(TagNameState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO
		parsingContext.setCurrentToken(new EndTagToken(""));
		parsingContext.readerHandle().unread(ch);
		context.setTokenizeState(tagNameState);
	}

}
