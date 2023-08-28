package com.github.webicitybrowser.spiderhtml.tokenize;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.EOFToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public class AttributeValueSingleQuotedState implements TokenizeState {

	private final AfterAttributeValueQuotedState afterAttributeValueQuotedState;
	private final CharacterReferenceState characterReferenceState;

	public AttributeValueSingleQuotedState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.characterReferenceState = initializer.getTokenizeState(CharacterReferenceState.class);
		this.afterAttributeValueQuotedState = initializer.getTokenizeState(AfterAttributeValueQuotedState.class);
	}
	
	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) {
		switch (ch) {
		case '\'':
			context.setTokenizeState(afterAttributeValueQuotedState);
			break;
		case '&':
			context.setReturnState(this);
			context.setTokenizeState(characterReferenceState);
			break;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			parsingContext.getCurrentToken(StartTagToken.class)
				.appendToAttributeValue('\uFFFD');
			break;
		case -1:
				context.recordError(ParseError.EOF_IN_TAG);
				context.emit(new EOFToken());
				break;
		default:
			parsingContext.getCurrentToken(StartTagToken.class)
				.appendToAttributeValue(ch);
		}
	}

}
