package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class CommentLessThanSignBangDashState implements TokenizeState {

	private CommentLessThanSignBangDashDashState commentLessThanSignBangDashDashState;
	private final CommentEndDashState commentEndDashState;

	public CommentLessThanSignBangDashState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentLessThanSignBangDashDashState = initializer.getTokenizeState(CommentLessThanSignBangDashDashState.class);
		this.commentEndDashState = initializer.getTokenizeState(CommentEndDashState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(commentLessThanSignBangDashDashState);
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentEndDashState);
		}
	}

}
