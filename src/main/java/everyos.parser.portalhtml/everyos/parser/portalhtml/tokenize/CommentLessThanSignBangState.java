package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;

public class CommentLessThanSignBangState implements TokenizeState {
	
	private final HTMLParserContext context;
	
	private final CommentState commentState;
	private final CommentLessThanSignBangDashState commentLessThanSignBangDashState;
	
	public CommentLessThanSignBangState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentState = context.getTokenizeState(CommentState.class);
		this.commentLessThanSignBangDashState = context.getTokenizeState(CommentLessThanSignBangDashState.class);
	}

	@Override
	public TokenizeState process(int ch) {
		switch(ch) {
		case '-':
			return commentLessThanSignBangDashState;
		default:
			context.unread(ch);
			return commentState;
		}
	}

}
