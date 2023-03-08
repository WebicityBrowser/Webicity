package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;

public class CommentLessThanSignBangDashDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentEndState commentEndState;

	public CommentLessThanSignBangDashDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentEndState = context.getTokenizeState(CommentEndState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
		case '>':
		case -1:
			context.unread(ch);
			return commentEndState;
		default:
			context.recordError(ParseError.NESTED_COMMENT);
			return commentEndState;
		}
	}

}
