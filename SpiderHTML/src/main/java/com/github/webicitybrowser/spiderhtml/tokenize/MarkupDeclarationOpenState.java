package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.ReaderHandle;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;

public class MarkupDeclarationOpenState implements TokenizeState {

	private final DoctypeState doctypeState;
	private final CommentStartState commentStartState;
	private final BogusCommentState bogusCommentState;

	public MarkupDeclarationOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.doctypeState = initializer.getTokenizeState(DoctypeState.class);
		this.commentStartState = initializer.getTokenizeState(CommentStartState.class);
		this.bogusCommentState = initializer.getTokenizeState(BogusCommentState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		ReaderHandle reader = parsingContext.readerHandle();
		parsingContext.readerHandle().unread(ch);
		if (reader.lookahead(2).equals("--")) {
			reader.eat(2);
			parsingContext.setCurrentToken(new CommentToken(""));
			context.setTokenizeState(commentStartState);
		} else if (reader.lookahead(7).equalsIgnoreCase("DOCTYPE")) {
			reader.eat(7);
			context.setTokenizeState(doctypeState);
		} else {
			context.recordError(ParseError.INCORRECTLY_OPENED_COMMENT);
			CommentToken token = new CommentToken("");
			parsingContext.setCurrentToken(token);
			context.setTokenizeState(bogusCommentState);
		}
	}

}
