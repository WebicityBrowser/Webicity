package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;

public class DataState implements TokenizeState {

	private final CharacterReferenceState characterReferenceState;
	private final TagOpenState tagOpenState;

	public DataState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.characterReferenceState = initializer.getTokenizeState(CharacterReferenceState.class);
		this.tagOpenState = initializer.getTokenizeState(TagOpenState.class);
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		case '&':
			context.setReturnState(this);
			context.setTokenizeState(characterReferenceState);
			break;
		case '<':
			context.setTokenizeState(tagOpenState);
			break;
		case -1:
			context.emit(new EOFToken());
			break;
		default:
			// TODO: Chunk characters into strings in certain cases, for optimization?
			if (ch == 0) {
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			}
			context.emit(new CharacterToken(ch));
		}
	}
	
}
