package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class TagOpenState implements TokenizeState {
	
	private final MarkupDeclarationOpenState markupDeclarationOpenState;
	private EndTagOpenState endTagOpenState;
	private final TagNameState tagNameState;

	public TagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.markupDeclarationOpenState = initializer.getTokenizeState(MarkupDeclarationOpenState.class);
		this.tagNameState = initializer.getTokenizeState(TagNameState.class);
		this.endTagOpenState = initializer.getTokenizeState(EndTagOpenState.class);
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
		default:
			// TODO
			throw new UnsupportedOperationException();
		}
	}

}
