package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;

public class NumericCharacterReferenceEndState implements TokenizeState {

	private final HTMLParserContext context;

	public NumericCharacterReferenceEndState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
	}
	
	@Override
	public TokenizeState process(int ch) {
		// TODO Extremely complex logic
		context.unread(ch);
		context.setTemporaryBuffer("");
		context.getTemporaryBuffer().appendCodePoint(ch);
		context.flushCodePointsConsumedAsACharacterReference();
		return context.getReturnState();
	}

}
