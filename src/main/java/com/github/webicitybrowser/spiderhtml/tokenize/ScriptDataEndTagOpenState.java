package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;

public class ScriptDataEndTagOpenState implements TokenizeState {

	private final ScriptDataEndTagNameState scriptDataEndTagNameState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEndTagOpenState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataEndTagNameState = initializer.getTokenizeState(ScriptDataEndTagNameState.class);
		this.scriptDataState = initializer.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			parsingContext.setCurrentToken(new EndTagToken(""));
			context.setTokenizeState(scriptDataEndTagNameState);
		} else {
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('/'));
			context.setTokenizeState(scriptDataState);
		}
	}

}
