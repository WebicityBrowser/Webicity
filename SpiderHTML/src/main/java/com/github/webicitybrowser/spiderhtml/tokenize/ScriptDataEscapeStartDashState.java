package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataEscapeStartDashState implements TokenizeState {

	private final ScriptDataEscapeStartDashDashState scriptDataEscapeStartDashDashState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEscapeStartDashState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataEscapeStartDashDashState = initializer.getTokenizeState(ScriptDataEscapeStartDashDashState.class);
		this.scriptDataState = initializer.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(scriptDataEscapeStartDashDashState);
			context.emit(new CharacterToken('-'));
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataState);
		}
	}

}
