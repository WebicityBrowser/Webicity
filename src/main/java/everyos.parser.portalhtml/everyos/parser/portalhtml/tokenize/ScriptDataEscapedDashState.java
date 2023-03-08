package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class ScriptDataEscapedDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataEscapedDashDashState scriptDataEscapedDashDashState;
	private final ScriptDataEscapedLessThanSignState scriptDataEscapedLessThanSignState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataEscapedDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataEscapedDashDashState = context.getTokenizeState(ScriptDataEscapedDashDashState.class);
		this.scriptDataEscapedLessThanSignState = context.getTokenizeState(ScriptDataEscapedLessThanSignState.class);
		this.scriptDataEscapedState = context.getTokenizeState(ScriptDataEscapedState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '-':
				context.emit(new CharacterToken('-'));
				return scriptDataEscapedDashDashState;
				
			case '<':
				return scriptDataEscapedLessThanSignState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				context.emit(new CharacterToken('\uFFFD'));
				return scriptDataEscapedState;
				
			case -1:
				context.recordError(ParseError.EOF_IN_SCRIPT_HTML_COMMENT_LIKE_TEXT);
				context.emit(new EOFToken());
				return null;
				
			default:
				context.emit(new CharacterToken(ch));
				return scriptDataEscapedState;
		}
	}

}
