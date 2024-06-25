package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class CommentLessThanSignBangDashDashState implements TokenizeState {

	private final CommentEndState commentEndState;

	public CommentLessThanSignBangDashDashState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentEndState = initializer.getTokenizeState(CommentEndState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '>':
		case -1:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentEndState);
			break;
		default:
			context.recordError(ParseError.NESTED_COMMENT);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentEndState);
		}
	}

}
