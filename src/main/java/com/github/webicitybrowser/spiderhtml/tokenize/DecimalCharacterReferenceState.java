package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class DecimalCharacterReferenceState implements TokenizeState {

	private final NumericCharacterReferenceEndState numericCharacterReferenceEndState;

	public DecimalCharacterReferenceState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.numericCharacterReferenceEndState = initializer.getTokenizeState(NumericCharacterReferenceEndState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ASCIIUtil.isASCIIDigit(ch)) {
			int refCode = parsingContext.getCharacterReferenceCode();
			refCode *= 10;
			refCode += ASCIIUtil.fromASCIINumericalCharacter(ch);
			parsingContext.setCharacterReferenceCode(refCode);
		} else if (ch == ';') {
			context.setTokenizeState(numericCharacterReferenceEndState);
		} else {
			context.recordError(ParseError.MISSING_SEMICOLON_AFTER_CHARACTER_REFERENCE);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(numericCharacterReferenceEndState);
		}
	}

}
