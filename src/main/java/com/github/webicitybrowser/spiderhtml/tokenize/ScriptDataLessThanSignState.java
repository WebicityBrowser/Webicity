package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataLessThanSignState implements TokenizeState {

	private final ScriptDataEndTagOpenState scriptDataEndTagOpenState;
	private final ScriptDataEscapeStartState scriptDataEscapeStartState;
	private final ScriptDataState scriptDataState;

	public ScriptDataLessThanSignState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataEndTagOpenState = initializer.getTokenizeState(ScriptDataEndTagOpenState.class);
		this.scriptDataEscapeStartState = initializer.getTokenizeState(ScriptDataEscapeStartState.class);
		this.scriptDataState = initializer.getTokenizeState(ScriptDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '/':
			parsingContext.resetTemporaryBuffer();
			context.setTokenizeState(scriptDataEndTagOpenState);
			break;
		case '!':
			context.setTokenizeState(scriptDataEscapeStartState);
			context.emit(new CharacterToken('<'));
			context.emit(new CharacterToken('!'));
			break;
		default:
			context.emit(new CharacterToken('<'));
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(scriptDataState);
			break;
		}
	}

}
