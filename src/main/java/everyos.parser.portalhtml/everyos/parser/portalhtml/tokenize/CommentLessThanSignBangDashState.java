package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;

public class CommentLessThanSignBangDashState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentLessThanSignBangDashDashState commentLessThanSignBangDashDashState;
	private final CommentEndDashState commentEndDashState;

	public CommentLessThanSignBangDashState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentLessThanSignBangDashDashState = context.getTokenizeState(CommentLessThanSignBangDashDashState.class);
		this.commentEndDashState = context.getTokenizeState(CommentEndDashState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
		case '-':
			return commentLessThanSignBangDashDashState;
		default:
			context.unread(ch);
			return commentEndDashState;
		}
	}

}
