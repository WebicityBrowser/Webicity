package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EndTagToken;

public class RCDataEndTagNameState implements TokenizeState {

	private final BeforeAttributeNameState beforeAttributeNameState;
	private final SelfClosingStartTagState selfClosingStartTagState;
	private final DataState dataState;
	private final RCDataState rcDataState;
	
	public RCDataEndTagNameState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.beforeAttributeNameState = initializer.getTokenizeState(BeforeAttributeNameState.class);
		this.selfClosingStartTagState = initializer.getTokenizeState(SelfClosingStartTagState.class);
		this.dataState = initializer.getTokenizeState(DataState.class);
		this.rcDataState = initializer.getTokenizeState(RCDataState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		EndTagToken endTagToken = parsingContext.getCurrentToken(EndTagToken.class);
		if (ASCIIUtil.isASCIIAlpha(ch)) {
			int lowerChar = ASCIIUtil.toASCIILowerCase(ch);
			endTagToken.appendToName(lowerChar);
			parsingContext.appendToTemporaryBuffer(ch);
		} else if (
			context.isAppropriateEndTagToken(endTagToken) &&
			handleAppropriateEndTagToken(context, parsingContext, ch, endTagToken)
		) {
			return;
		} else {
			emitCharactersNoTag(context, parsingContext);
			parsingContext.readerHandle().unread(ch);
			context.setTokenizeState(rcDataState);
		}
	}
	
	private boolean handleAppropriateEndTagToken(SharedContext context, ParsingContext parsingContext, int ch, EndTagToken endTagToken) {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			context.setTokenizeState(beforeAttributeNameState);
			return true;
		case '/':
			context.setTokenizeState(selfClosingStartTagState);
			return true;
		case '>':
			context.setTokenizeState(dataState);
			context.emit(parsingContext.getCurrentToken(EndTagToken.class));
			return true;
		default:
			return false;
		}
	}

	private void emitCharactersNoTag(SharedContext context, ParsingContext parsingContext) {
		context.emit(new CharacterToken('<'));
		context.emit(new CharacterToken('/'));
		String tempBuff = parsingContext.getTemporaryBuffer();
		for (int i = 0; i < tempBuff.length(); i++) {
			context.emit(new CharacterToken(tempBuff.codePointAt(i)));
		}
	}

}
