package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.util.CharUtil;

public class HexadecimalCharacterReferenceStartState implements TokenizeState {

	private final HTMLParserContext context;
	private final HexadecimalCharacterReferenceState hexadecimalCharacterReferenceState;

	public HexadecimalCharacterReferenceStartState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.hexadecimalCharacterReferenceState = context.getTokenizeState(HexadecimalCharacterReferenceState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIHexDigit(ch)) {
			context.unread(ch);
			return hexadecimalCharacterReferenceState;
		} else {
			context.recordError(ParseError.ABSENCE_OF_DIGITS_IN_NUMERIC_CHARACTER_REFERENCE);
			context.flushCodePointsConsumedAsACharacterReference();
			context.unread(ch);
			return context.getReturnState();
		}
	}

}
