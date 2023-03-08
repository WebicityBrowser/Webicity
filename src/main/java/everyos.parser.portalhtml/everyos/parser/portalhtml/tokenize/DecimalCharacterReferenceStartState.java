package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.util.CharUtil;

public class DecimalCharacterReferenceStartState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final DecimalCharacterReferenceState decimalCharacterReferenceState;

	public DecimalCharacterReferenceStartState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.decimalCharacterReferenceState = context.getTokenizeState(DecimalCharacterReferenceState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIINumeric(ch)) {
			context.unread(ch);
			return decimalCharacterReferenceState;
		} else {
			context.recordError(ParseError.ABSENCE_OF_DIGITS_IN_NUMERIC_CHARACTER_REFERENCE);
			context.flushCodePointsConsumedAsACharacterReference();
			context.unread(ch);
			return context.getReturnState();
		}
	}

}
