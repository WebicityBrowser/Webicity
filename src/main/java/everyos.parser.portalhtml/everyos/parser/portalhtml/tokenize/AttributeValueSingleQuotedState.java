package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.StartTagToken;

public class AttributeValueSingleQuotedState implements TokenizeState {

private final HTMLParserContext context;
	
	private final AfterAttributeValueQuotedState afterAttributeValueQuotedState;
	private final AttributeValueSingleQuotedState attributeValueSingleQuotedState;
	private final CharacterReferenceState characterReferenceState;

	public AttributeValueSingleQuotedState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.afterAttributeValueQuotedState = context.getTokenizeState(AfterAttributeValueQuotedState.class);
		this.attributeValueSingleQuotedState = context.getTokenizeState(AttributeValueSingleQuotedState.class);
		this.characterReferenceState = context.getTokenizeState(CharacterReferenceState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\'':
				return afterAttributeValueQuotedState;
				
			case '&':
				context.setReturnState(attributeValueSingleQuotedState);
				return characterReferenceState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				//TODO: Is this possible to reach from an end tag?
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.appendToAttributeValue('\uFFFD');
				}
				return this;
				
			case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				return null;
				
			default:
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.appendToAttributeValue(ch);
				}
				return this;
		}
	}

}
