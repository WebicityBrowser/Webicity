package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;

public class NumericCharacterReferenceState implements TokenizeState {

	private final HexadecimalCharacterReferenceStartState hexadecimalCharacterReferenceStartState;
	private final DecimalCharacterReferenceStartState decimalCharacterReferenceStartState;

	public NumericCharacterReferenceState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.hexadecimalCharacterReferenceStartState = initializer.getTokenizeState(HexadecimalCharacterReferenceStartState.class);
		this.decimalCharacterReferenceStartState = initializer.getTokenizeState(DecimalCharacterReferenceStartState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.setCharacterReferenceCode(0);
		switch (ch) {
		case 'x':
		case 'X':
			parsingContext.appendToTemporaryBuffer(ch);
			context.setTokenizeState(hexadecimalCharacterReferenceStartState);
			break;
		default:
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(decimalCharacterReferenceStartState);
		}
	}

}
