package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class DataState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final CharacterReferenceState characterReferenceState;
	private final TagOpenState tagOpenState;

	public DataState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.characterReferenceState = context.getTokenizeState(CharacterReferenceState.class);
		this.tagOpenState = context.getTokenizeState(TagOpenState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '&':
				context.setReturnState(this);
				return characterReferenceState;
			
			case '<':
				return tagOpenState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				context.emit(new CharacterToken(ch));
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
