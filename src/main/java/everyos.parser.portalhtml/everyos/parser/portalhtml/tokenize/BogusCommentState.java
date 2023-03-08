package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class BogusCommentState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final DataState dataState;

	public BogusCommentState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.dataState = context.getTokenizeState(DataState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
			case '>':
				context.emit(context.getCurrentToken());
				return dataState;
				
			case -1:
				context.emit(context.getCurrentToken());
				context.emit(new EOFToken());
				return null;
				
			case 0:
				context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
				{
					CommentToken curToken = ((CommentToken) context.getCurrentToken());
					curToken.appendToValue('\uFFFD');
				}
				return this;
				
			default:
				{
					CommentToken curToken = ((CommentToken) context.getCurrentToken());
					curToken.appendToValue(ch);
				}
				return this;
		}
	}

}
