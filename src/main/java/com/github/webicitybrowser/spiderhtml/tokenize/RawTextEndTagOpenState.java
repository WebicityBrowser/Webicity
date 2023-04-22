package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class RawTextEndTagOpenState implements TokenizeState {

	private final RawTextEndTagNameState rawTextEndTagNameState;
	private final RawTextState rawTextState;

	public RawTextEndTagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.rawTextEndTagNameState = initializer.getTokenizeState(RawTextEndTagNameState.class);
		this.rawTextState = initializer.getTokenizeState(RawTextState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.setCurrentToken(new EndTagToken(""));
			context.setTokenizeState(rawTextEndTagNameState);
		} else {
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('/'));
			context.setTokenizeState(rawTextState);
		}
	}

}
