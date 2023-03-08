package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.UnicodeDictionary;
import everyos.parser.portalhtml.context.HTMLParserContext;

public class NamedCharacterReferenceState implements TokenizeState {

	private final HTMLParserContext context;
	private AmbiguousAmpersandState ambiguousAmpersandState;

	public NamedCharacterReferenceState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.ambiguousAmpersandState = context.getTokenizeState(AmbiguousAmpersandState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		context.unread(ch);
		
		UnicodeDictionary dict = context.getUnicodeDictionary();
		
		String foundRef = "";
		for (String ref: dict.getEntityNames()) {
			if (
				ref.length() > foundRef.length() &&
				ref.equals('&' + new String(context.lookahead(ref.length()-1)) + ';')
			) {
				foundRef = ref;
			}
		}
		
		if (!foundRef.isEmpty()) {
			// TODO
			if (context.lookahead(1) == ";") {
				context.eat(1);
			} else {
				context.recordError(ParseError.MISSING_SEMICOLON_AFTER_CHARACTER_REFERENCE);
			}
			context.setTemporaryBuffer("");
			StringBuffer tmpBuff = context.getTemporaryBuffer();
			for (int cp: dict.getCodePointsForNamedEntity(foundRef)) {
				tmpBuff.appendCodePoint(cp);
			}
			context.eat(foundRef.length());
			
			context.flushCodePointsConsumedAsACharacterReference();
			return context.getReturnState();
		} else {
			context.flushCodePointsConsumedAsACharacterReference();
			
			return ambiguousAmpersandState;
		}
	}
	
}