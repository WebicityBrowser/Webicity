package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class ScriptDataState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final ScriptDataLessThanSignState scriptDataLessThanState;

	public ScriptDataState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.scriptDataLessThanState = context.getTokenizeState(ScriptDataLessThanSignState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '<':
				return scriptDataLessThanState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				context.emit(new CharacterToken('\uFFFD'));
				break;
				
			case -1:
				context.emit(new EOFToken());
				return null;
				
			default:
				context.emit(new CharacterToken(ch));
				break;
		}
		
		return this;
	}
	
}
