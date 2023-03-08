package everyos.parser.portalhtml.emit.mode;

import java.util.function.Consumer;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLDocumentLeaf;
import everyos.parser.portalhtml.tree.HTMLLeaf;
import everyos.parser.portalhtml.tree.HTMLTreeBuilder;

public class InitialInsertionMode implements InsertionMode {
	
	private final HTMLParserContext context;
	private final BeforeHTMLInsertionMode beforeHTMLInsertionMode;

	public InitialInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.beforeHTMLInsertionMode = context.getInsertionMode(BeforeHTMLInsertionMode.class);
	}

	@Override
	public void emit(Token token) {
		if (TokenUtil.isWhiteSpaceToken(token)) {
			// Empty body
		} else if (token instanceof CommentToken) {
			TreeUtil.insertAComment(
				context, (CommentToken) token,
				TreeUtil.getDocumentTailInsertionLocation(context));
		} else if (token instanceof DoctypeToken dtToken) {
			// TODO
			HTMLTreeBuilder treeBuilder = context.getTreeBuilder();
			HTMLDocumentLeaf documentLeaf = treeBuilder.getDocument();
			HTMLLeaf documentTypeLeaf = treeBuilder.createDocumentType(documentLeaf, dtToken.getName(), "", "");
			documentLeaf.append(documentTypeLeaf);
			context.setCurrentInsertionMode(beforeHTMLInsertionMode);
		} else {
			// TODO
			context.setCurrentInsertionMode(beforeHTMLInsertionMode);
		}
	}

}
