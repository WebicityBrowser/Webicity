package everyos.parser.portalhtml.emit.mode;

import java.util.Stack;
import java.util.function.Consumer;

import everyos.parser.portalhtml.ParseError;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.util.LeafUtil;
import everyos.parser.portalhtml.emit.util.SpecialUtil;
import everyos.parser.portalhtml.emit.util.StackUtil;
import everyos.parser.portalhtml.emit.util.TokenUtil;
import everyos.parser.portalhtml.emit.util.TreeUtil;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.DoctypeToken;
import everyos.parser.portalhtml.tokens.EOFToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.tree.HTMLElementLeaf;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public class InBodyInsertionMode implements InsertionMode {

	private final HTMLParserContext context;
	
	private final InHeadInsertionMode inHeadInsertionMode;

	public InBodyInsertionMode(HTMLParserContext context, Consumer<InsertionMode> callback) {
		callback.accept(this);
		this.context = context;
		this.inHeadInsertionMode = context.getInsertionMode(InHeadInsertionMode.class);
	}
	
	@Override
	public void emit(Token token) {
		if (token instanceof CharacterToken) {
			processCharacterToken((CharacterToken) token);
			return;
		} else if (token instanceof CommentToken) {
			TreeUtil.insertAComment(context, (CommentToken) token, null);
			return;
		} else if (token instanceof DoctypeToken) {
			context.recordError(ParseError.GENERIC);
			return;
		} else if (token instanceof EOFToken) {
			// TODO
			return;
		} else if (token instanceof StartTagToken) {
			if (processStartTagToken((StartTagToken) token)) {
				return;
			}
		} else if (token instanceof EndTagToken) {
			if (processEndTagToken((EndTagToken) token)) {
				return;
			}
		}
	}

	private void processCharacterToken(CharacterToken token) {
		if (token.getCharacter() == 0) {
			context.recordError(ParseError.GENERIC);
			return;
		}
		
		TreeUtil.reconstructActiveFormattingElements(context);
		TreeUtil.insertCharacter(context, token);
		if (!TokenUtil.isWhiteSpaceToken(token)) {
			context
				.getInsertionContext()
				.setFramesetOk(false);
		}
	}

	private boolean processStartTagToken(StartTagToken token) {
		switch (token.getName()) {
		case "html":
			context.recordError(ParseError.GENERIC);
			// TODO
			return true;
		case "base":
		case "basefont":
		case "bgsound":
		case "link":
		case "meta":
		case "noframes":
		case "script":
		case "style":
		case "template":
		case "title":
			inHeadInsertionMode.emit(token);
			return true;
		case "body":
			context.recordError(ParseError.GENERIC);
			// TODO
			return true;
		case "frameset":
			context.recordError(ParseError.GENERIC);
			// TODO
			return true;
		case "address":
		case "article":
		case "aside":
		case "blockquote":
		case "center":
		case "details":
		case "dialog":
		case "dir":
		case "div":
		case "dl":
		case "fieldset":
		case "figcaption":
		case "figure":
		case "footer":
		case "header":
		case "hgroup":
		case "main":
		case "menu":
		case "nav":
		case "ol":
		case "p":
		case "section":
		case "summary":
		case "ul":
			if (StackUtil.hasElementInButtonScope(context, "p")) {
				closeAPElement();
			}
			TreeUtil.insertHTMLElement(context, token);
			return true;
		case "h1":
		case "h2":
		case "h3":
		case "h4":
		case "h5":
		case "h6":
			processHeaderStartTagToken(token);
			return true;
		// TODO: cases before
		case "area":
		case "br":
		case "embed":
		case "img":
		case "keygen":
		case "wbr":
			TreeUtil.reconstructActiveFormattingElements(context);
			TreeUtil.insertHTMLElement(context, token);
			StackUtil.pop(context);
			token.acknowledgeSelfClosingFlag();
			context.getInsertionContext().setFramesetOk(false);
			return true;
		// TODO: cases after
		default:
			TreeUtil.reconstructActiveFormattingElements(context);
			TreeUtil.insertHTMLElement(context, token);
			return true;
		}
	}

	private boolean processEndTagToken(EndTagToken token) {
		switch (token.getName()) {
		default:
			processOtherEndTagToken(token);
			return true;
		}
	}
	
	private void processHeaderStartTagToken(StartTagToken token) {
		if (StackUtil.hasElementInButtonScope(context, "p")) {
			closeAPElement();
		}
		HTMLLeaf currentNode = StackUtil.getCurrentNode(context);
		if (LeafUtil.isHTMLElementWithOneOfName(
			currentNode, "h1", "h2", "h3", "h4", "h5", "h6")
		) {
			context.recordError(ParseError.GENERIC);
		}
		TreeUtil.insertHTMLElement(context, token);
	}

	private void closeAPElement() {
		TreeUtil.generateImpliedEndTags(context, "p");
		HTMLLeaf currentNode = StackUtil.getCurrentNode(context);
		if (!LeafUtil.isHTMLElementWithName(currentNode, "p")) {
			context.recordError(ParseError.GENERIC);
		}
		StackUtil.popUntilHTMLElement(context, "p");
	}

	private void processOtherEndTagToken(EndTagToken token) {
		Stack<HTMLLeaf> stack = context
			.getInsertionContext()
			.getOpenElementStack();
		int pos = stack.size();
		while (true) {
			pos--;
			HTMLLeaf leaf = stack.get(pos);
			String name = token.getName();
			if (LeafUtil.isHTMLElementWithName(leaf, name)) {
				TreeUtil.generateImpliedEndTags(context, name);
				if (!stack.peek().equals(leaf)) {
					context.recordError(ParseError.GENERIC);
				}
				while (!StackUtil.pop(context).equals(leaf));
				break;
			} else if (leaf instanceof HTMLElementLeaf element && SpecialUtil.isSpecial(element)) {
				context.recordError(ParseError.GENERIC);
				break;
			}
		}
	}

}
