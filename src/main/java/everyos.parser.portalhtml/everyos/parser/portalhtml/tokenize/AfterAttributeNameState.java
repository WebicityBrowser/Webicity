package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.StartTagToken;

public class AfterAttributeNameState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final BeforeAttributeValueState beforeAttributeValueState;
	private final DataState dataState;
	private final AttributeNameState attributeNameState;

	public AfterAttributeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.selfClosingStartTagState = context.getTokenizeState(SelfClosingStartTagState.class);
		this.beforeAttributeValueState = context.getTokenizeState(BeforeAttributeValueState.class);
		this.dataState = context.getTokenizeState(DataState.class);
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
				return selfClosingStartTagState;
				
			case '=':
				return beforeAttributeValueState;
				
			case '>':
				context.emit(context.getCurrentToken());
				return dataState;
				
			case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				return null;
				
			default:
				//TODO: Is this possible to reach from an end tag?
				{
					StartTagToken curToken = ((StartTagToken) context.getCurrentToken());
					curToken.startNewAttribute();
				}
				
				context.unread(ch);
				return attributeNameState;
		}
	}

}
