package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class ScriptDataEscapedState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEscapedDashState scriptDataEscapedDashState;
	private final ScriptDataEscapedLessThanSignState scriptDataEscapedLessThanSignState;

	public ScriptDataEscapedState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEscapedDashState = context.getTokenizeState(ScriptDataEscapedDashState.class);
		this.scriptDataEscapedLessThanSignState = context.getTokenizeState(ScriptDataEscapedLessThanSignState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '-':
				context.emit(new CharacterToken('-'));
				return scriptDataEscapedDashState;
				
			case '<':
				return scriptDataEscapedLessThanSignState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				context.emit(new CharacterToken('\uFFFD'));
				return this;
				
			case -1:
				context.recordError(ParseError.EOF_IN_SCRIPT_HTML_COMMENT_LIKE_TEXT);
				context.emit(new EOFToken());
				return null;
				
			default:
				context.emit(new CharacterToken(ch));
				return this;
		}
	}

}
