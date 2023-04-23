package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class CommentEndState implements TokenizeState {
	
	private final DataState dataState;
	private final CommentEndBangState commentEndBangState;
	private final CommentState commentState;

	public CommentEndState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.commentEndBangState = initializer.getTokenizeState(CommentEndBangState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		CommentToken commentToken = parsingContext.getCurrentToken(CommentToken.class);
		switch (ch) {
		case '>':
			context.setTokenizeState(dataState);
			context.emit(commentToken);
			break;
		case '!':
			context.setTokenizeState(commentEndBangState);
			break;
		case '-':
			commentToken.appendToData('-');
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(commentToken);
			context.emit(new EOFToken());
			break;
		default:
			commentToken.appendToData('-');
			commentToken.appendToData('-');
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
