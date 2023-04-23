package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class CommentStartDashState implements TokenizeState {

	private final CommentEndState commentEndState;
	private final DataState dataState;
	private final CommentState commentState;

	public CommentStartDashState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentEndState = initializer.getTokenizeState(CommentEndState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(commentEndState);
			break;
		case '>':
			context.recordError(ParseError.ABRUPT_CLOSING_OF_EMPTY_COMMENT);
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(CommentToken.class));
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(parsingContext.getCurrentToken(CommentToken.class));
			context.emit(new EOFToken());
			break;
		default:
			CommentToken commentToken = parsingContext.getCurrentToken(CommentToken.class);
			commentToken.appendToData('-');
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
