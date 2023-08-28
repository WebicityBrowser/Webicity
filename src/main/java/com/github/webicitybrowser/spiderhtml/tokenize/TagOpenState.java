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
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class TagOpenState implements TokenizeState {
	
	private final MarkupDeclarationOpenState markupDeclarationOpenState;
	private final EndTagOpenState endTagOpenState;
	private final TagNameState tagNameState;
	private final BogusCommentState bogusCommentState;
	private final DataState dataState;

	public TagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.markupDeclarationOpenState = initializer.getTokenizeState(MarkupDeclarationOpenState.class);
		this.tagNameState = initializer.getTokenizeState(TagNameState.class);
		this.endTagOpenState = initializer.getTokenizeState(EndTagOpenState.class);
		this.bogusCommentState = initializer.getTokenizeState(BogusCommentState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.setCurrentToken(new StartTagToken(""));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(tagNameState);
			
			return;
		}
		
		switch (ch) {
		case '!':
			context.setTokenizeState(markupDeclarationOpenState);
			break;
		case '/':
			context.setTokenizeState(endTagOpenState);
			break;
		case '?':
			context.recordError(ParseError.UNEXPECTED_QUESTION_MARK_INSTEAD_OF_TAG_NAME);
			parsingContext.setCurrentToken(new CommentToken(""));
			context.setTokenizeState(bogusCommentState);
			break;
		case -1:
			context.recordError(ParseError.EOF_BEFORE_TAG_NAME);
			context.emit(new CharacterToken('<'));
			context.emit(new EOFToken());
			break;
		default:
			context.recordError(ParseError.UNEXPECTED_FIRST_CHARACTER_OF_TAG_NAME);
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(dataState);
		}
	}

}
