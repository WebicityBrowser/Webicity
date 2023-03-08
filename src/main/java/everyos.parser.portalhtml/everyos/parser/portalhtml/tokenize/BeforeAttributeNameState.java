package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.StartTagToken;

public class BeforeAttributeNameState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final AfterAttributeNameState afterAttributeNameState;
	private final AttributeNameState attributeNameState;

	public BeforeAttributeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.afterAttributeNameState = context.getTokenizeState(AfterAttributeNameState.class);
		this.attributeNameState = context.getTokenizeState(AttributeNameState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
				return this;
			
			case '/':
			case '>':
			case -1:
				context.unread(ch);
				return afterAttributeNameState;
				
			case '=':
				context.recordError(ParseError.UNEXPECTED_EQUALS_SIGN_BEFORE_ATTRIBUTE_NAME);
				//TODO: Is this possible to reach from an end tag?
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.startNewAttribute();
				}
				return attributeNameState;
				
			default:
				((StartTagToken) context.getCurrentToken())
					.startNewAttribute();
				context.unread(ch);
				return attributeNameState;
		}
	}

}
