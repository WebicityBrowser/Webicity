package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class RCDataState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final CharacterReferenceState characterReferenceState;
	private final RCDataLessThanSignState rcdataLessThanSignState;

	public RCDataState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.characterReferenceState = context.getTokenizeState(CharacterReferenceState.class);
		this.rcdataLessThanSignState = context.getTokenizeState(RCDataLessThanSignState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '&':
				context.setReturnState(this);
				return characterReferenceState;
			
			case '<':
				return rcdataLessThanSignState;
				
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
