package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;

public class CommentLessThanSignState implements TokenizeState {
	
	private final CommentLessThanSignBangState commentLessThanSignBangState;
	private final CommentState commentState;

	public CommentLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentLessThanSignBangState = initializer.getTokenizeState(CommentLessThanSignBangState.class);
		this.commentState = initializer.getTokenizeState(CommentState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		CommentToken commentToken = parsingContext.getCurrentToken(CommentToken.class);
		switch (ch) {
		case '!':
			commentToken.appendToData(ch);
			context.setTokenizeState(commentLessThanSignBangState);
			break;
		case '<':
			commentToken.appendToData(ch);
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(commentState);
		}
	}

}
