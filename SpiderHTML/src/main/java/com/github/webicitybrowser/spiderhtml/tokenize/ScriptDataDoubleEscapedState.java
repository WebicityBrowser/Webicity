package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class ScriptDataDoubleEscapedState implements TokenizeState {

	private final ScriptDataDoubleEscapedDashState scriptDataDoubleEscapedDashState;
	private final ScriptDataDoubleEscapedLessThanSignState scriptDataDoubleEscapedLessThanSignState;
	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;

	public ScriptDataDoubleEscapedState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataDoubleEscapedDashState = initializer.getTokenizeState(ScriptDataDoubleEscapedDashState.class);
		this.scriptDataDoubleEscapedLessThanSignState = initializer.getTokenizeState(ScriptDataDoubleEscapedLessThanSignState.class);
		this.scriptDataDoubleEscapedState = initializer.getTokenizeState(ScriptDataDoubleEscapedState.class); 
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch (ch) {
		case '-':
			context.setTokenizeState(scriptDataDoubleEscapedDashState);
			context.emit(new CharacterToken('-'));
			break;
		case '<':
			context.setTokenizeState(scriptDataDoubleEscapedLessThanSignState);
			context.emit(new CharacterToken('<'));
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			context.setReturnState(scriptDataDoubleEscapedState);
			context.emit(new  CharacterToken('\uFFFD'));
			break;
		case -1:
			context.recordError(ParseError.EOF_IN_SCRIPT_HTML_COMMENT_LIKE_TEXT);
			context.emit(new EOFToken());
			break;
		default:
			context.setReturnState(scriptDataDoubleEscapedState);
			context.emit(new CharacterToken(ch));
		}
	}

}
