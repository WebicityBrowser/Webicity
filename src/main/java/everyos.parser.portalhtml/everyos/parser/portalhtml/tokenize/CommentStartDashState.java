package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class CommentStartDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentEndState commentEndState;
	private final DataState dataState;
	private final CommentState commentState;

	public CommentStartDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentEndState = context.getTokenizeState(CommentEndState.class);
		this.dataState = context.getTokenizeState(DataState.class);
		this.commentState = context.getTokenizeState(CommentState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
			case '-':
				return commentEndState;
			case '>':
				context.recordError(ParseError.ABRUPT_CLOSING_OF_EMPTY_COMMENT);
				context.emit(context.getCurrentToken());
				return dataState;
			case -1:
				context.recordError(ParseError.EOF_IN_COMMENT);
				context.emit(context.getCurrentToken());
				context.emit(new EOFToken());
				return null;
			default:
				((CommentToken) context.getCurrentToken()).appendToValue('-');
				context.unread(ch);
				return commentState;
		}
	}

}
