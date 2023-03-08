package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class DoctypeState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final BeforeDoctypeNameState beforeDoctypeNameState;

	public DoctypeState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeDoctypeNameState = context.getTokenizeState(BeforeDoctypeNameState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			return beforeDoctypeNameState;
		case '>':
			context.unread(ch);
			return beforeDoctypeNameState;
		case -1:
			context.recordError(ParseError.EOF_IN_DOCTYPE);
			DoctypeToken token = new DoctypeToken();
			token.setForceQuirks(true);
			context.emit(token);
			context.emit(new EOFToken());
			return null;
		default:
			context.recordError(ParseError.MISSING_WHITESPACE_BEFORE_DOCTYPE_NAME);
			context.unread(ch);
			return beforeDoctypeNameState;
		}
	}

}
