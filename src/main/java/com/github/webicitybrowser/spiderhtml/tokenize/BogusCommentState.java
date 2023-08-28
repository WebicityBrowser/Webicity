package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class BogusCommentState implements TokenizeState {

	private final DataState dataState;

	public BogusCommentState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '>':
			context.emit(parsingContext.getCurrentToken(CommentToken.class));
			context.setTokenizeState(dataState);
			break;
		case -1:
			context.emit(parsingContext.getCurrentToken(CommentToken.class));
			context.emit(new EOFToken());
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			parsingContext.getCurrentToken(CommentToken.class).appendToData('\uFFFD');
			break;
		default:
			parsingContext.getCurrentToken(CommentToken.class).appendToData(ch);
			break;
		}
	}
	
}
