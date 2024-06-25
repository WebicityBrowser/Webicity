package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.DoctypeToken;

public class DoctypeNameState implements TokenizeState {
	
	private final DataState dataState;

	public DoctypeNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.dataState = initializer.getTokenizeState(DataState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		case '>': {
			context.setTokenizeState(dataState);
			DoctypeToken doctypeToken = parsingContext.getCurrentToken(DoctypeToken.class);
			context.emit(doctypeToken);
			break;
		}
		default: {
			// TODO
			DoctypeToken doctypeToken = parsingContext.getCurrentToken(DoctypeToken.class);
			doctypeToken.appendToName(ASCIIUtil.toASCIILowerCase(ch));
		}
		}
	}

}
