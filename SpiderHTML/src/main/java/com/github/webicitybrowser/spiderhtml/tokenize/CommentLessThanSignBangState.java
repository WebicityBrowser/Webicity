package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class CommentLessThanSignBangState implements TokenizeState {
	
	private final CommentLessThanSignBangDashState commentLessThanSignBangDashState;
	private final CommentState commentState;

	public CommentLessThanSignBangState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentLessThanSignBangDashState = initializer.getTokenizeState(CommentLessThanSignBangDashState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(commentLessThanSignBangDashState);
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
