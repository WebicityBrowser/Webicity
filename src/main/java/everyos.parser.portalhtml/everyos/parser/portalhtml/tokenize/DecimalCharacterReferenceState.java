package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.util.CharUtil;

public class DecimalCharacterReferenceState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final NumericCharacterReferenceEndState numericCharacterReferenceEndState;

	public DecimalCharacterReferenceState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.numericCharacterReferenceEndState = context.getTokenizeState(NumericCharacterReferenceEndState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIINumeric(ch)) {
			int refCode = context.getCharacterReferenceCode();
			refCode *= 10;
			refCode += ch - 0x30;
			context.setCharacterReferenceCode(refCode);
			return this;
		} else if (ch == ';') {
			return numericCharacterReferenceEndState;
		} else {
			context.recordError(ParseError.MISSING_SEMICOLON_AFTER_CHARACTER_REFERENCE);
			context.unread(ch);
			return numericCharacterReferenceEndState;
		}
	}

}
