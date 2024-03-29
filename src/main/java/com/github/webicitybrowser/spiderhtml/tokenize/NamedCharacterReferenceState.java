package com.github.webicitybrowser.spiderhtml.tokenize;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.github.webicitybrowser.spec.html.parse.ParseError;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spiderhtml.context.ParsingContext;
import com.github.webicitybrowser.spiderhtml.context.ParsingInitializer;
import com.github.webicitybrowser.spiderhtml.context.ReaderHandle;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.misc.TokenizeLogic;

public class NamedCharacterReferenceState implements TokenizeState {
	
	private final Map<String, int[]> referenceLookup = new HashMap<>();
	
	private final AmbiguousAmpersandState ambiguousAmpersandState;
	private final int longestReferenceLength;

	public NamedCharacterReferenceState(ParsingInitializer initializer, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.ambiguousAmpersandState = initializer.getTokenizeState(AmbiguousAmpersandState.class);
		this.longestReferenceLength = setupReferenceLookup(initializer.getSettings().getUnicodeLookup());
	}

	@Override
	public void process(SharedContext context, ParsingContext parsingContext, int ch) throws IOException {
		parsingContext.readerHandle().unread(ch);
		int [] referenceCodepoints = consumeReference(parsingContext.readerHandle());
		
		if (referenceCodepoints != null) {
			// TODO: Historical attribute handling
			if (referenceCodepoints[referenceCodepoints.length - 1] != ';') {
				context.recordError(ParseError.MISSING_SEMICOLON_AFTER_CHARACTER_REFERENCE);
			}
			parsingContext.resetTemporaryBuffer();
			for (int codepoint: referenceCodepoints) {
				parsingContext.appendToTemporaryBuffer(codepoint);
			}
			TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
			context.setTokenizeState(context.getReturnState());
		} else {
			TokenizeLogic.flushCodePointsConsumedAsACharacterReference(context, parsingContext);
			context.setTokenizeState(ambiguousAmpersandState);
		}
	}
	
	private int setupReferenceLookup(CharacterReferenceLookup unicodeLookup) {
		int longestReferenceLength = 0;
		for (String referenceName: unicodeLookup.getEntityNames()) {
			referenceLookup.put(referenceName, unicodeLookup.getCodePointsForNamedEntity(referenceName));
			longestReferenceLength = Math.max(longestReferenceLength, referenceName.length());
		}
		
		return longestReferenceLength;
	}

	private int[] consumeReference(ReaderHandle reader) throws IOException {
		String comparison = reader.lookahead(longestReferenceLength - 1);
		
		for (int i = comparison.length(); i > 0; i--) {
			String referenceName = '&' + comparison.substring(0, i);
			if (referenceLookup.containsKey(referenceName)) {
				reader.eat(i);
				return referenceLookup.get(referenceName);
			}
		}
		
		return null;
	}

}
