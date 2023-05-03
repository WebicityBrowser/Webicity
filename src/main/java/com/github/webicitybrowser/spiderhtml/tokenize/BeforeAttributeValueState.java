package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.TagToken;

public class BeforeAttributeValueState implements TokenizeState {

	private final AttributeValueDoubleQuotedState attributeValueDoubleQuotedState;
	private final AttributeValueSingleQuotedState attributeValueSingleQuotedState;
	private final AttributeValueUnquotedState attributeValueUnquotedState;
	private final DataState dataState;

	public BeforeAttributeValueState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.attributeValueDoubleQuotedState = initializer.getTokenizeState(AttributeValueDoubleQuotedState.class);
		this.attributeValueSingleQuotedState = initializer.getTokenizeState(AttributeValueSingleQuotedState.class);
		this.attributeValueUnquotedState = initializer.getTokenizeState(AttributeValueUnquotedState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
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
		case '>':
			context.recordError(ParseError.MISSING_ATTRIBUTE_VALUE);
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(TagToken.class));
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(attributeValueUnquotedState);
		}
	}

}
