package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.DoctypeToken;
import com.github.webicitybrowser.spiderhtml.util.ASCIIUtil;

public class BeforeDoctypeNameState implements TokenizeState {

	private final DoctypeNameState doctypeNameState;

	public BeforeDoctypeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.doctypeNameState = initializer.getTokenizeState(DoctypeNameState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		default:
			// TODO
			DoctypeToken token = new DoctypeToken();
			token.appendToName(ASCIIUtil.toASCIILowerCase(ch));
			parsingContext.setCurrentToken(token);
			context.setTokenizeState(doctypeNameState);
		}
	}

}
