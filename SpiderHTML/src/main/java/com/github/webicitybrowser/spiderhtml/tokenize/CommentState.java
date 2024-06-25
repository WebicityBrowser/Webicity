package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class CommentState implements TokenizeState {
	
	private final CommentLessThanSignState commentLessThanSignState;
	private final CommentEndDashState commentEndDashState;

	public CommentState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.commentLessThanSignState = initializer.getTokenizeState(CommentLessThanSignState.class);
		this.commentEndDashState = initializer.getTokenizeState(CommentEndDashState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		CommentToken commentToken = parsingContext.getCurrentToken(CommentToken.class);
		switch (ch) {
		case '<':
			commentToken.appendToData(ch);
			context.setTokenizeState(commentLessThanSignState);
			break;
		case '-':
			context.setTokenizeState(commentEndDashState);
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			commentToken.appendToData('\uFFFD');
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(commentToken);
			context.emit(new EOFToken());
			break;
		default:
			commentToken.appendToData(ch);
		}
	}

}
