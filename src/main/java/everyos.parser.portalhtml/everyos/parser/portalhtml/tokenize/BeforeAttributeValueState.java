package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;

public class BeforeAttributeValueState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final AttributeValueDoubleQuotedState attributeValueDoubleQuotedState;
	private final AttributeValueSingleQuotedState attributeValueSingleQuotedState;
	private final DataState dataState;
	private final AttributeValueUnquotedState attributeValueUnquotedState;

	public BeforeAttributeValueState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.attributeValueDoubleQuotedState = context.getTokenizeState(AttributeValueDoubleQuotedState.class);
		this.attributeValueSingleQuotedState = context.getTokenizeState(AttributeValueSingleQuotedState.class);
		this.dataState = context.getTokenizeState(DataState.class);
		this.attributeValueUnquotedState = context.getTokenizeState(AttributeValueUnquotedState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '\t':
			case '\n':
			case '\f':
			case ' ':
				return this;
				
			case '"':
				return attributeValueDoubleQuotedState;
				
			case '\'':
				return attributeValueSingleQuotedState;
				
			case '>':
				context.recordError(ParseError.MISSING_ATTRIBUTE_VALUE);
				context.emit(context.getCurrentToken());
				return dataState;
				
			default:
				context.unread(ch);
				return attributeValueUnquotedState;
		}
	}

}
