package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.StartTagToken;

public class AttributeValueUnquotedState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final BeforeAttributeNameState beforeAttributeNameState;
	private final CharacterReferenceState characterReferenceState;
	private final DataState dataState;

	public AttributeValueUnquotedState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeAttributeNameState = context.getTokenizeState(BeforeAttributeNameState.class);
		this.characterReferenceState = context.getTokenizeState(CharacterReferenceState.class);
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
				return beforeAttributeNameState;
				
			case '&':
				context.setReturnState(this);
				return characterReferenceState;
				
			case '>':
				context.emit(context.getCurrentToken());
				return dataState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				//TODO: Is this possible to reach from an end tag?
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.appendToAttributeValue('\uFFFD');
				}
				return this;
				
			case '"':
			case '\'':
			case '<':
			case '=':
			case '`':
				context.recordError(ParseError.UNEXPECTED_CHARACTER_IN_UNQUOTED_ATTRIBUTE_VALUE);
				
			case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				return null;
				
			default:
				break;
		}
		
		StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
		curToken.appendToAttributeValue(ch);
		return this;
	}

}
