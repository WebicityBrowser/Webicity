package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;

public class NumericCharacterReferenceState implements TokenizeState {

	private final HTMLParserContext context;
	private final HexadecimalCharacterReferenceStartState hexadecimalCharacterReferenceStartState;
	private final DecimalCharacterReferenceStartState decimalCharacterReferenceStartState;

	public NumericCharacterReferenceState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.hexadecimalCharacterReferenceStartState = context.getTokenizeState(HexadecimalCharacterReferenceStartState.class);
		this.decimalCharacterReferenceStartState = context.getTokenizeState(DecimalCharacterReferenceStartState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		context.setCharacterReferenceCode(0);
		
		switch (ch) {
		case 'x':
		case 'X':
			context.getTemporaryBuffer().appendCodePoint(ch);
			return hexadecimalCharacterReferenceStartState;
		default:
			context.unread(ch);
			return decimalCharacterReferenceStartState;
		}
	}

}
