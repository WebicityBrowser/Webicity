package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.TagToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class TagNameState implements TokenizeState {

	private final DataState dataState;

	public TagNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		TagToken currentTagToken = parsingContext.getCurrentToken(TagToken.class);
		
		// TODO
		switch (ch) {
		case '>':
			context.setTokenizeState(dataState);
			context.emit(currentTagToken);
			break;
		default:
			currentTagToken.appendToName(ASCIIUtil.toASCIILowerCase(ch));
		}
	}

}
