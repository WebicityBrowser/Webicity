package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataDoubleEscapedLessThanSignState implements TokenizeState {

	private final ScriptDataDoubleEscapeEndState scriptDataDoubleEscapeEndState;
	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;

	public ScriptDataDoubleEscapedLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataDoubleEscapeEndState = initializer.getTokenizeState(ScriptDataDoubleEscapeEndState.class);
		this.scriptDataDoubleEscapedState = initializer.getTokenizeState(ScriptDataDoubleEscapedState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ch == '/') {
			parsingContext.resetTemporaryBuffer();
			context.setTokenizeState(scriptDataDoubleEscapeEndState);
			context.emit(new CharacterToken('/'));
		} else {
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataDoubleEscapedState);
		}
	}

}
