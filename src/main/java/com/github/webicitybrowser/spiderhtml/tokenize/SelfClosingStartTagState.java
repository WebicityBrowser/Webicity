package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.TagToken;

public class SelfClosingStartTagState implements TokenizeState {
	
	private final DataState dataState;
	private final BeforeAttributeNameState beforeAttributeNameState;

	public SelfClosingStartTagState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '>':
			TagToken tagToken = parsingContext.getCurrentToken(TagToken.class);
			tagToken.setSelfClosingTag(true);
			context.setTokenizeState(dataState);
			context.emit(tagToken);
		case -1:
			context.recordError(ParseError.EOF_IN_TAG);
			context.emit(new EOFToken());
			break;
		default:
			context.recordError(ParseError.UNEXPECTED_SOLIDUS_IN_TAG);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(beforeAttributeNameState);
		}
	}

}
