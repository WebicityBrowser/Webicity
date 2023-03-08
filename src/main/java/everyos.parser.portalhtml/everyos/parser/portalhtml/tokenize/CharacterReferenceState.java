package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.util.CharUtil;

public class CharacterReferenceState implements TokenizeState {

	private final HTMLParserContext context;
	private final NamedCharacterReferenceState namedCharacterReferenceState;
	private final NumericCharacterReferenceState numericCharacterReferenceState;

	public CharacterReferenceState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.namedCharacterReferenceState = context.getTokenizeState(NamedCharacterReferenceState.class);
		this.numericCharacterReferenceState = context.getTokenizeState(NumericCharacterReferenceState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		context.setTemporaryBuffer("&");
		if (CharUtil.isASCIIAlpha(ch)) {
			return namedCharacterReferenceState;
		} else if (ch == '#') {
			context.getTemporaryBuffer().append(ch);
			return numericCharacterReferenceState;
		} else {
			context.flushCodePointsConsumedAsACharacterReference();
			context.unread(ch);
			return context.getReturnState();
		}
	}

}
