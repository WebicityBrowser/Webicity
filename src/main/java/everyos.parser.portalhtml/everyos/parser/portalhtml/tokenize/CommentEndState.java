package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;

public class CommentEndState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final DataState dataState;
	private final CommentEndBangState commentEndBangState;
	private final CommentState commentState;

	public CommentEndState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.dataState = context.getTokenizeState(DataState.class);
		this.commentEndBangState = context.getTokenizeState(CommentEndBangState.class);
		this.commentState = context.getTokenizeState(CommentState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
		case '>':
			context.emit(context.getCurrentToken());
			return dataState;
		case '!':
			return commentEndBangState;
		case '-':
			((CommentToken) context.getCurrentToken()).appendToValue('-');
			return this;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(context.getCurrentToken());
			context.emit(new EOFToken());
			return null;
		default:
			((CommentToken) context.getCurrentToken()).appendToValue('-');
			((CommentToken) context.getCurrentToken()).appendToValue('-');
			context.unread(ch);
			return commentState;
		}
	}

}
