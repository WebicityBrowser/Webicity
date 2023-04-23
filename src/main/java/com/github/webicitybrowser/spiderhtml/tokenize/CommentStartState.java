package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;

public class CommentStartState implements TokenizeState {

	private final CommentStartDashState commentStartDashState;
	private final DataState dataState;
	private final CommentState commentState;

	public CommentStartState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentStartDashState = initializer.getTokenizeState(CommentStartDashState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(commentStartDashState);
			break;
		case '>':
			context.recordError(ParseError.ABRUPT_CLOSING_OF_EMPTY_COMMENT);
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(CommentToken.class));
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
