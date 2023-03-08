package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.StartTagToken;

public class SelfClosingStartTagState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final DataState dataState;
	private final BeforeAttributeNameState beforeAttributeNameState;

	public SelfClosingStartTagState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.dataState = context.getTokenizeState(DataState.class);
		this.beforeAttributeNameState = context.getTokenizeState(BeforeAttributeNameState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '>':
				((StartTagToken) context.getCurrentToken()).setSelfClosingFlag();
				context.emit(context.getCurrentToken());
				return dataState;
				
			case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				return null;
			
			default:
				context.recordError(ParseError.UNEXPECTED_SOLIDUS_IN_TAG);
				context.unread(ch);
				return beforeAttributeNameState;
		}
	}

}
