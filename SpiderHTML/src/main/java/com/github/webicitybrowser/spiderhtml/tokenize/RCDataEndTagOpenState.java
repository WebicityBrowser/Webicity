package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;

public class RCDataEndTagOpenState implements TokenizeState {

	private final RCDataEndTagNameState rcDataEndTagNameState;
	private final RCDataState rcDataState;

	public RCDataEndTagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.rcDataEndTagNameState = initializer.getTokenizeState(RCDataEndTagNameState.class);
		this.rcDataState = initializer.getTokenizeState(RCDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.setCurrentToken(new EndTagToken(""));
			context.setTokenizeState(rcDataEndTagNameState);
		} else {
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('/'));
			context.setTokenizeState(rcDataState);
		}
	}

}
