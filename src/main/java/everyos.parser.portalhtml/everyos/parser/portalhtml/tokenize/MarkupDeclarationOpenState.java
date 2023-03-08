package everyos.parser.portalhtml.tokenize;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tokens.CommentToken;

public class MarkupDeclarationOpenState implements TokenizeState {

	private final HTMLParserContext context;
	
	private final CommentStartState commentStartState;
	private final DoctypeState doctypeState;
	private final BogusCommentState bogusCommentState;

	public MarkupDeclarationOpenState(HTMLParserContext context, Consumer<TokenizeState> callback) {
		callback.accept(this);
		this.context = context;
		this.commentStartState = context.getTokenizeState(CommentStartState.class);
		this.doctypeState = context.getTokenizeState(DoctypeState.class);
		this.bogusCommentState = context.getTokenizeState(BogusCommentState.class);
	}
	
	@Override
	public TokenizeState process(int ch) {
		context.unread(ch);
		if (context.lookahead(2).equals("--")) {
			context.eat(2);
			context.setCurrentToken(new CommentToken());
			return commentStartState;
		} else if (context.lookahead(7).equalsIgnoreCase("DOCTYPE")) {
			context.eat(7);
			return doctypeState;
		} else if (context.lookahead(7).equals("[CDATA[")) {
			context.eat(7);
			//TODO
			return null;
		} else {
			context.recordError(ParseError.INCORRECTLY_OPENED_COMMENT);
			context.setCurrentToken(new CommentToken());
			return bogusCommentState;
		}
	}

}
