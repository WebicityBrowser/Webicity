package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class BeforeAttributeValueState implements TokenizeState {

	private final AttributeValueDoubleQuotedState attributeValueDoubleQuotedState;
	private final AttributeValueSingleQuotedState attributeValueSingleQuotedState;

	public BeforeAttributeValueState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.attributeValueDoubleQuotedState = initializer.getTokenizeState(AttributeValueDoubleQuotedState.class);
		this.attributeValueSingleQuotedState = initializer.getTokenizeState(AttributeValueSingleQuotedState.class);
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
		case '"':
			context.setTokenizeState(attributeValueDoubleQuotedState);
			break;
		case '\'':
			context.setTokenizeState(attributeValueSingleQuotedState);
			break;
		default:
			throw new UnsupportedOperationException();
		}
	}

}
