package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataEscapeStartState implements TokenizeState {

	private final ScriptDataEscapeStartDashState scriptDataEscapeStartDashState;
	private final ScriptDataState scriptDataState;

	public ScriptDataEscapeStartState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataEscapeStartDashState = initializer.getTokenizeState(ScriptDataEscapeStartDashState.class);
		this.scriptDataState = initializer.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(scriptDataEscapeStartDashState);
			context.emit(new CharacterToken('-'));
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataState);
		}
	}

}
