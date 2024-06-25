package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AfterAttributeValueQuotedState implements TokenizeState {

	private final BeforeAttributeNameState beforeAttributeNameState;
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final DataState dataState;

	public AfterAttributeValueQuotedState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
		this.selfClosingStartTagState = initializer.getTokenizeState(SelfClosingStartTagState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeAttributeNameState);
			break;
		case '/':
			context.setTokenizeState(selfClosingStartTagState);
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
			context.recordError(ParseError.MISSING_WHITESPACE_BETWEEN_ATTRIBUTES);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(beforeAttributeNameState);
		}
	}

}
