package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.TagToken;

public class CommentState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final CommentLessThanSignState commentLessThanSignState;
	private final CommentEndDashState commentEndDashState;

	public CommentState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentLessThanSignState = context.getTokenizeState(CommentLessThanSignState.class);
		this.commentEndDashState = context.getTokenizeState(CommentEndDashState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch (ch) {
		case '<':
			((CommentToken) context.getCurrentToken()).appendToValue(ch);
			return commentLessThanSignState;
		case '-':
			return commentEndDashState;
		case 0:
			context.recordError(ParseError.UNEXPECTED_NULL_CHARACTER);
			((TagToken) context.getCurrentToken()).appendToName('\uFFFD');
			return this;
		case -1:
			context.recordError(ParseError.EOF_IN_COMMENT);
			context.emit(context.getCurrentToken());
			context.emit(new EOFToken());
			return null;
		default:
			((CommentToken) context.getCurrentToken()).appendToValue(ch);
			return this;
		}
	}

}
