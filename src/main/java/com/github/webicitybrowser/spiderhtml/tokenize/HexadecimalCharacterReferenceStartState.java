package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;

public class HexadecimalCharacterReferenceStartState implements TokenizeState {

	private final HexadecimalCharacterReferenceState hexadecimalCharacterReferenceState;

	public HexadecimalCharacterReferenceStartState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.hexadecimalCharacterReferenceState = initializer.getTokenizeState(HexadecimalCharacterReferenceState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		if (ASCIIUtil.isASCIIHexDigit(ch)) {
			context.setTokenizeState(hexadecimalCharacterReferenceState);
		} else {
			context.recordError(ParseError.ABSENCE_OF_DIGITS_IN_NUMERIC_CHARACTER_REFERENCE);
			TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
			context.setTokenizeState(context.getReturnState());
		}
	}

}
