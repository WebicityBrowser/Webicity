package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;

public class ScriptDataDoubleEscapeStartState implements TokenizeState {

	private final ScriptDataDoubleEscapedState scriptDataDoubleEscapedState;
	private final ScriptDataEscapedState scriptDataEscapedState;

	public ScriptDataDoubleEscapeStartState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.scriptDataDoubleEscapedState = initializer.getTokenizeState(ScriptDataDoubleEscapedState.class);
		this.scriptDataEscapedState = initializer.getTokenizeState(ScriptDataEscapedState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		switch(ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
		case '/':
		case '>':
			if (parsingContext.getTemporaryBuffer().equals("script")) {
				context.setTokenizeState(scriptDataDoubleEscapedState);
			} else {
				context.setTokenizeState(scriptDataEscapedState);
			}
			context.emit(new CharacterToken(ch));
		default:
			if (ASCIIUtil.isASCIIAlpha(ch)) {
				parsingContext.appendToTemporaryBuffer(ASCIIUtil.toASCIILowerCase(ch));
				context.emit(new CharacterToken(ch));
			} else {
				parsingContext.readerHandle().unread(ch);
				context.setTokenizeState(scriptDataEscapedState);
			}
		}
	}

}
