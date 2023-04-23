package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class CommentEndBangState implements TokenizeState {

	private final CommentEndDashState commentEndDashState;
	private final DataState dataState;
	private final CommentState commentState;

	public CommentEndBangState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentEndDashState = initializer.getTokenizeState(CommentEndDashState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		CommentToken commentToken = parsingContext.getCurrentToken(CommentToken.class);
		switch (ch) {
		case '-':
			commentToken.appendToData('-');
			commentToken.appendToData('!');
			context.setTokenizeState(commentEndDashState);
			break;
		case '>':
			context.recordError(ParseError.INCORRECTLY_CLOSED_COMMENT);
			context.setTokenizeState(dataState);
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(commentToken);
			context.emit(new EOFToken());
			break;
		default:
			commentToken.appendToData('-');
			commentToken.appendToData('-');
			commentToken.appendToData('!');
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
