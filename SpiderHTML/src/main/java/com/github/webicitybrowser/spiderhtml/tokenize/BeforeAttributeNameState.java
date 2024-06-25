package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class BeforeAttributeNameState implements TokenizeState {

	private final AttributeNameState attributeNameState;
	private final AfterAttributeNameState afterAttributeNameState;

	public BeforeAttributeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.attributeNameState = initializer.getTokenizeState(AttributeNameState.class);
		this.afterAttributeNameState = initializer.getTokenizeState(AfterAttributeNameState.class);
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
		case '/':
		case '>':
		case -1:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(afterAttributeNameState);
			break;
		case '=':
			context.recordError(ParseError.UNEXPECTED_EQUALS_SIGN_BEFORE_ATTRIBUTE_NAME);
			StartTagToken token = parsingContext.getCurrentToken(StartTagToken.class);
			token.startNewAttribute();
			token.appendToAttributeName(ch);
			context.setTokenizeState(attributeNameState);
			break;
		default:
			parsingContext.getCurrentToken(StartTagToken.class).startNewAttribute();
			context.setTokenizeState(attributeNameState);
			parsingContext.readerHandle().unread(ch);
		}
 	}
	
}
