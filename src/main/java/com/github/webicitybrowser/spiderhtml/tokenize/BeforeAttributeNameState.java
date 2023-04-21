package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class BeforeAttributeNameState implements TokenizeState {

	private final AttributeNameState attributeNameState;

	public BeforeAttributeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.attributeNameState = initializer.getTokenizeState(AttributeNameState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			break;
		default:
			parsingContext.getCurrentToken(StartTagToken.class).startNewAttribute();
			context.setTokenizeState(attributeNameState);
			parsingContext.readerHandle().unread(ch);
		}
 	}
	
}
