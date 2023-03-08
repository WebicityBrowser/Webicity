package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.util.CharUtil;

public class AmbiguousAmpersandState implements TokenizeState {

	private final HTMLParserContext context;

	public AmbiguousAmpersandState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
	}
	
	@Override
	public TokenizeState process(int ch) {
		if (CharUtil.isASCIIAlphanumeric(ch)) {
			// TODO
			context.emit(new CharacterToken(ch));
			return this;
		} else if (ch == ';') {
			context.recordError(ParseError.UNKNOWN_NAMED_CHARACTER_REFERENCE);
			context.unread(ch);
			return context.getReturnState();
		} else {
			context.unread(ch);
			return context.getReturnState();
		}
	}

}
