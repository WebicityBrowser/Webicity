package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.util.CharUtil;

public class AttributeNameState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final AfterAttributeNameState afterAttributeNameState;
	private final BeforeAttributeValueState beforeAttributeValueState;

	public AttributeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.afterAttributeNameState = context.getTokenizeState(AfterAttributeNameState.class);
		this.beforeAttributeValueState = context.getTokenizeState(BeforeAttributeValueState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
			case '/':
			case '>':
			case -1:
				context.unread(ch);
				return afterAttributeNameState;
				
			case '=':
				return beforeAttributeValueState;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				//TODO: Is this possible to reach from an end tag?
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.appendToAttributeName(ch);
				}
				return this;
				
			case '"':
			case '\'':
			case '<':
				context.recordError(ParseError.UNEXPECTED_CHARACTER_IN_ATTRIBUTE_NAME);
				// Intentional fall-through
			default:
				StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
				curToken.appendToAttributeName(CharUtil.toASCIILowerCase(ch));
				return this;
		}
	}

}
