package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AfterAttributeNameState implements TokenizeState {
	
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final BeforeAttributeValueState beforeAttributeValueState;
	private final DataState dataState;
	private final AttributeNameState attributeNameState;
	
	public AfterAttributeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.selfClosingStartTagState = initializer.getTokenizeState(SelfClosingStartTagState.class);
		this.beforeAttributeValueState = initializer.getTokenizeState(BeforeAttributeValueState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.attributeNameState = initializer.getTokenizeState(AttributeNameState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			break;
		case '/':
			context.setTokenizeState(selfClosingStartTagState);
			break;
		case '=':
			context.setTokenizeState(beforeAttributeValueState);
			break;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(StartTagToken.class));
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_TAG);
			context.emit(new EOFToken());
			break;
		default:
			parsingContext.getCurrentToken(StartTagToken.class).startNewAttribute();
			context.setTokenizeState(attributeNameState);
			parsingContext.readerHandle().unread(ch);
		}
	}

}
