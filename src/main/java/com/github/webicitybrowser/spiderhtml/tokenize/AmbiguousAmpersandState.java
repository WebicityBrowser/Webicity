package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AmbiguousAmpersandState implements TokenizeState {

	public AmbiguousAmpersandState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		if (ASCIIUtil.isASCIIAlphanumeric(ch)) {
			if (TokenizeLogic.isInAttribute(context)) {
				parsingContext.getCurrentToken(StartTagToken.class).appendToAttributeValue(ch);
			} else {
				context.emit(new CharacterToken(ch));
			}
		} else {
			if (ch == ';') {
				context.recordError(ParseError.UNKNOWN_NAMED_CHARACTER_REFERENCE);
			}
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(context.getReturnState());
		}
	}

}
