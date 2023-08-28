package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.CommentToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;

public class EndTagOpenState implements TokenizeState {
	
	private TagNameState tagNameState;
	private DataState dataState;
	private BogusCommentState bogusCommentState;

	public EndTagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.tagNameState = initializer.getTokenizeState(TagNameState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.bogusCommentState = initializer.getTokenizeState(BogusCommentState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.setCurrentToken(new EndTagToken(""));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(tagNameState);
			return;
		}
		switch (ch) {
		case '>':
			context.recordError(ParseError.MISSING_END_TAG_NAME);
			context.setTokenizeState(dataState);
			break;
		case -1:
			context.recordError(ParseError.EOF_BEFORE_TAG_NAME);
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('/'));
			context.emit(new EOFToken());
			break;
		default:
			context.recordError(ParseError.INVALID_FIRST_CHARACTER_OF_TAG_NAME);
			parsingContext.setCurrentToken(new CommentToken(""));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(bogusCommentState);
		}
	}

}
