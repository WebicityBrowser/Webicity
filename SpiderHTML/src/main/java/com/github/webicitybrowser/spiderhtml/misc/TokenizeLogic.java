package com.github.webicitybrowser.spiderhtml.misc;

import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.CharacterToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.tokenize.AttributeValueDoubleQuotedState;
import com.github.webicitybrowser.spiderhtml.tokenize.AttributeValueSingleQuotedState;
import com.github.webicitybrowser.spiderhtml.tokenize.AttributeValueUnquotedState;

public final class TokenizeLogic {

	private TokenizeLogic() {}

	public static void flushCodePointsConsumedAsACharacterReference(SharedContext context, ParsingContext parsingContext) {
		if (isInAttribute(context)) {
			flushCodepointsToAttribute(parsingContext);
		} else {
			emitCodepoints(context, parsingContext);
		}
	}
	
	public static boolean isInAttribute(SharedContext context) {
		if (context.getReturnState() == null) {
			return false;
		}
		
		Class<?> stateClass = context.getReturnState().getClass();
		return
			stateClass == AttributeValueDoubleQuotedState.class ||
			stateClass == AttributeValueSingleQuotedState.class ||
			stateClass == AttributeValueUnquotedState.class;
	}

	private static void emitCodepoints(SharedContext context, ParsingContext parsingContext) {
		String buffer = parsingContext.getTemporaryBuffer();
		for (int i = 0; i < buffer.length(); i++) {
			context.emit(new CharacterToken(buffer.codePointAt(i)));
		}
	}
	
	private static void flushCodepointsToAttribute(ParsingContext parsingContext) {
		String buffer = parsingContext.getTemporaryBuffer();
		for (int i = 0; i < buffer.length(); i++) {
			StartTagToken token = parsingContext.getCurrentToken(StartTagToken.class);
			token.appendToAttributeValue(buffer.codePointAt(i));
		}
	}
	
}
