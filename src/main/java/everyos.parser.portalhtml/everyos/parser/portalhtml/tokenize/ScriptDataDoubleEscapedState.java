package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class ScriptDataDoubleEscapedState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataDoubleEscapedDashState scriptDataDoubleEscapedDashState;
	private final ScriptDataDoubleEscapedLessThanSignState scriptDataDoubleEscapedLessThanSignState;

	public ScriptDataDoubleEscapedState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataDoubleEscapedDashState = context.getTokenizeState(ScriptDataDoubleEscapedDashState.class);
		this.scriptDataDoubleEscapedLessThanSignState = context.getTokenizeState(ScriptDataDoubleEscapedLessThanSignState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '-':
				context.emit(new CharacterToken('-'));
				return scriptDataDoubleEscapedDashState;
				
			case '<':
				context.emit(new CharacterToken('<'));
				return scriptDataDoubleEscapedLessThanSignState;
				
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
