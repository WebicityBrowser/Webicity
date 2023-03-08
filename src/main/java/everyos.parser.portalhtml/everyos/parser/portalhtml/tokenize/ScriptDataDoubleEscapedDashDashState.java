package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class ScriptDataDoubleEscapedDashDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final ScriptDataDoubleEscapedLessThanSignState scriptDataDoubleEscapedLessThanSignState;
	private final ScriptDataState scriptDataState;
	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;

	public ScriptDataDoubleEscapedDashDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataDoubleEscapedLessThanSignState = context.getTokenizeState(ScriptDataDoubleEscapedLessThanSignState.class);
		this.scriptDataState = context.getTokenizeState(ScriptDataState.class);
		this.scriptDataDoubleEscapedState = context.getTokenizeState(ScriptDataDoubleEscapedState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '-':
				context.emit(new CharacterToken('-'));
				
			case '<':
				context.emit(new CharacterToken('<'));
				return scriptDataDoubleEscapedLessThanSignState;
				
			case '>':
				context.emit(new CharacterToken('>'));
				return scriptDataState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				context.emit(new CharacterToken('\uFFFD'));
				return scriptDataDoubleEscapedState;
				
			case -1:
				context.recordError(ParseError.EOF_IN_SCRIPT_HTML_COMMENT_LIKE_TEXT);
				context.emit(new EOFToken());
				return null;
				
			default:
				context.emit(new CharacterToken(ch));
				return scriptDataDoubleEscapedState;
		}
	}

}
