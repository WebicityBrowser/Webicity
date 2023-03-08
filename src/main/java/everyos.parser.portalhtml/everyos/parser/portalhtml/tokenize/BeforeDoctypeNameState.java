package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.util.CharUtil;

public class BeforeDoctypeNameState implements TokenizeState {

	private final HTMLParserContext context;
	private final DoctypeNameState doctypeNameState;
	private final DataState dataState;

	public BeforeDoctypeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.doctypeNameState = context.getTokenizeState(DoctypeNameState.class);
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '\t':
		case '\f':
		case '\n':
		case ' ':
			return this;
		case 0:
		{
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			DoctypeToken token = new DoctypeToken();
			token.appendToName('\uFFFD');
			context.setCurrentToken(token);
			return doctypeNameState;
		}
		case '>':
		{
			context.recordError(ParseError.MISSING_DOCTYPE_NAME);
			DoctypeToken token = new DoctypeToken();
			token.setForceQuirks(true);
			context.emit(token);
			return dataState;
		}
		case -1:
		{
			context.recordError(ParseError.EOF_IN_DOCTYPE);
			DoctypeToken token = new DoctypeToken();
			token.setForceQuirks(true);
			context.emit(token);
			context.emit(new EOFToken());
			
			return null;
		}
		default:
		{
			DoctypeToken token = new DoctypeToken();
			token.appendToName(CharUtil.toASCIILowerCase(ch));
			context.setCurrentToken(token);
			return doctypeNameState;
		}
		}
	}

}
