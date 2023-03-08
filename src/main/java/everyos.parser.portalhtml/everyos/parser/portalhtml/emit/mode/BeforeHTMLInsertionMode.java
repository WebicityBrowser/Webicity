package everyos.parser.portalhtml.emit.mode;

import java.util.Stack;
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
import everyos.parser.portalhtml.tree.HTMLDocumentLeaf;
import everyos.parser.portalhtml.tree.HTMLLeaf;
import everyos.parser.portalhtml.tree.HTMLTreeBuilder;

public class BeforeHTMLInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final BeforeHeadInsertionMode beforeHeadInsertionMode;

	public BeforeHTMLInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeHeadInsertionMode = context.getInsertionMode(BeforeHeadInsertionMode.class);
	}
	
	@Override
	public void emit(Token token) {
		Stack<HTMLLeaf> openElementStack = context
			.getInsertionContext()
			.getOpenElementStack();
		if (token instanceof DoctypeToken) {
			context.recordError(ParseError.GENERIC);
		} else if (token instanceof CommentToken) {
			TreeUtil.insertAComment(
				context, (CommentToken) token,
				TreeUtil.getDocumentTailInsertionLocation(context));
		} else if (TokenUtil.isWhiteSpaceToken(token)) {
			// Empty body
		} else if (TokenUtil.isStartTag(token, "html")) {
			HTMLTreeBuilder treeBuilder = context.getTreeBuilder();
			HTMLLeaf element = TreeUtil.createAnElementForToken(
				context,
				(StartTagToken) token,
				TreeUtil.HTML_NAMESPACE,
				treeBuilder.getDocument());
			treeBuilder.getDocument().append(element);
			openElementStack.add(element);
			context.setCurrentInsertionMode(beforeHeadInsertionMode);
		} else if (token instanceof EndTagToken && !TokenUtil.isEndTag(token, "head", "body", "html", "br")) {
			context.recordError(ParseError.GENERIC);
		} else {
			HTMLTreeBuilder treeBuilder = context.getTreeBuilder();
			HTMLDocumentLeaf document = treeBuilder.getDocument();
			HTMLLeaf element = TreeUtil.createElement(context, "html", document);
			document.append(element);
			openElementStack.add(element);
			context.setCurrentInsertionMode(beforeHeadInsertionMode);
		}
	}

}
