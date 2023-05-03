package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class ScriptDataState implements TokenizeState {
	
	private final ScriptDataLessThanSignState scriptDataLessThanSignState;

	public ScriptDataState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataLessThanSignState = initializer.getTokenizeState(ScriptDataLessThanSignState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '<':
			context.setTokenizeState(scriptDataLessThanSignState);
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			context.emit(new CharacterToken('\uFFFD'));
			break;
		case -1:
			context.emit(new EOFToken());
			break;
		default:
			context.emit(new CharacterToken(ch));
		}
	}

}
