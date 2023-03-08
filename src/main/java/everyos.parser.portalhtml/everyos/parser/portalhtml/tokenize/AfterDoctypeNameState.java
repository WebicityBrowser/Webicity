package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class AfterDoctypeNameState implements TokenizeState {

	private final HTMLParserContext context;
	private final DataState dataState;

	public AfterDoctypeNameState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '\t':
		case '\n':
		case '\f':
		case ' ':
			return this;
		case '>':
			context.emit(context.getCurrentToken());
			return dataState;
		case -1:
			context.recordError(ParseError.EOF_IN_DOCTYPE);
			((DoctypeToken) context.getCurrentToken())
				.setForceQuirks(true);
			context.emit(context.getCurrentToken());
			context.emit(new EOFToken());
			return null;
		default:
			//TODO
			return this;
		}
	}

}
