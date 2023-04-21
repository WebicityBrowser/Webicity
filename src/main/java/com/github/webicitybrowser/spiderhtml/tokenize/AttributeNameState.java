package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class AttributeNameState implements TokenizeState {

	private final AfterAttributeNameState afterAttributeNameState;
	private final BeforeAttributeValueState beforeAttributeValueState;

	public AttributeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.afterAttributeNameState = initializer.getTokenizeState(AfterAttributeNameState.class);
		this.beforeAttributeValueState = initializer.getTokenizeState(BeforeAttributeValueState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		// TODO
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
		case '/':
		case '>':
		case -1:
			context.setTokenizeState(afterAttributeNameState);
			parsingContext.readerHandle().unread(ch);
			break;
		case '=':
			context.setTokenizeState(beforeAttributeValueState);
			break;
		default:
			parsingContext
				.getCurrentToken(StartTagToken.class)
				.appendToAttributeName(ASCIIUtil.toASCIILowerCase(ch));
		}
	}

}
