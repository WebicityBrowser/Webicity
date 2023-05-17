package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;

public class DecimalCharacterReferenceStartState implements TokenizeState {

	private final DecimalCharacterReferenceState decimalCharacterReferenceState;

	public DecimalCharacterReferenceStartState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.decimalCharacterReferenceState = initializer.getTokenizeState(DecimalCharacterReferenceState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		if (ASCIIUtil.isASCIIDigit(ch)) {
			context.setTokenizeState(decimalCharacterReferenceState);
		} else {
			context.recordError(ParseError.ABSENCE_OF_DIGITS_IN_NUMERIC_CHARACTER_REFERENCE);
			TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
			context.setTokenizeState(context.getReturnState());
		}
	}

}
