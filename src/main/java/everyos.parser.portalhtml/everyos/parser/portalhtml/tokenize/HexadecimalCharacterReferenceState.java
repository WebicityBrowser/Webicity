package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.util.CharUtil;

public class HexadecimalCharacterReferenceState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final NumericCharacterReferenceEndState numericCharacterReferenceEndState;

	public HexadecimalCharacterReferenceState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.numericCharacterReferenceEndState = context.getTokenizeState(NumericCharacterReferenceEndState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIINumeric(ch)) {
			int refCode = context.getCharacterReferenceCode();
			refCode *= 16;
			refCode += ch - 0x30;
			context.setCharacterReferenceCode(refCode);
			return this;
		} else if (CharUtil.isASCIIHexDigit(ch)) {
			int refCode = context.getCharacterReferenceCode();
			refCode *= 16;
			refCode += CharUtil.toASCIILowerCase(ch) - 0x57;
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
