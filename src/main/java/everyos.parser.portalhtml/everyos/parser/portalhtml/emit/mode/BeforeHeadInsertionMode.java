package everyos.parser.portalhtml.emit.mode;

import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public class BeforeHeadInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final InBodyInsertionMode inBodyInsertionMode;
	private final InHeadInsertionMode inHeadInsertionMode;

	public BeforeHeadInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.inBodyInsertionMode = context.getInsertionMode(InBodyInsertionMode.class);
		this.inHeadInsertionMode = context.getInsertionMode(InHeadInsertionMode.class);
	}
	
	@Override
	public void emit(Token token) {
		if (TokenUtil.isWhiteSpaceToken(token)) {
			// Empty Body
		} else if (token instanceof CommentToken) {
			TreeUtil.insertAComment(context, (CommentToken) token, null);
		} else if (token instanceof DoctypeToken) {
			context.recordError(ParseError.GENERIC);
		} else if (TokenUtil.isStartTag(token, "html")) {
			inBodyInsertionMode.emit(token);
		} else if (TokenUtil.isStartTag(token, "head")) {
			HTMLLeaf leave = TreeUtil.insertHTMLElement(context, (StartTagToken) token);
			context
				.getInsertionContext()
				.setHeadElementPointer(leave);
		} else if (token instanceof EndTagToken && !TokenUtil.isEndTag(token, "head", "body", "html", "br")) {
			context.recordError(ParseError.GENERIC);
		} else {
			HTMLLeaf leave = TreeUtil.insertHTMLElement(context, new StartTagToken("head"));
			context
				.getInsertionContext()
				.setHeadElementPointer(leave);
			context.setCurrentInsertionMode(inHeadInsertionMode);
			inHeadInsertionMode.emit(token);
		}
	}

}
