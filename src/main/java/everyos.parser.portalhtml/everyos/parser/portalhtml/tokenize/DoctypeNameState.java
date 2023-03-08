package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.util.CharUtil;

public class DoctypeNameState implements TokenizeState {

	private final HTMLParserContext context;
	private final AfterDoctypeNameState afterDoctypeNameState;
	private final DataState dataState;

	public DoctypeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.afterDoctypeNameState = context.getTokenizeState(AfterDoctypeNameState.class);
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			return afterDoctypeNameState;
		case '>':
			context.emit(context.getCurrentToken());
			return dataState;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);;
			((DoctypeToken) context.getCurrentToken()).appendToName('\uFFFD');
			return this;
		case -1:
			context.recordError(ParseError.EOF_IN_DOCTYPE);
			((DoctypeToken) context.getCurrentToken()).setForceQuirks(true);
			context.emit(context.getCurrentToken());
			context.emit(new EOFToken());
			return this;
		default:
			((DoctypeToken) context.getCurrentToken())
				.appendToName(CharUtil.toASCIILowerCase(ch));
			return this;
		}
	}

}
