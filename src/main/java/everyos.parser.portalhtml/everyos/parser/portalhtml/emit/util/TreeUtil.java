package everyos.parser.portalhtml.emit.util;

import java.util.Stack;

import everyos.parser.portalhtml.context.InsertionContext;
import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.emit.InsertionLocation;
import everyos.parser.portalhtml.emit.InsertionMode;
import everyos.parser.portalhtml.emit.mode.TextInsertionMode;
import everyos.parser.portalhtml.tokenize.RCDataState;
import everyos.parser.portalhtml.tokenize.RawtextState;
import everyos.parser.portalhtml.tokenize.TokenizeState;
import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.CommentToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken.TokenAttribute;
import everyos.parser.portalhtml.tree.HTMLAttribute;
import everyos.parser.portalhtml.tree.HTMLCommentLeaf;
import everyos.parser.portalhtml.tree.HTMLDocumentLeaf;
import everyos.parser.portalhtml.tree.HTMLElementLeaf;
import everyos.parser.portalhtml.tree.HTMLLeaf;
import everyos.parser.portalhtml.tree.HTMLTextLeaf;

public final class TreeUtil {
	
	public static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

	private TreeUtil() {}
	
	public static InsertionLocation getAppropriatePlaceForInsertingNode(HTMLParserContext context, HTMLLeaf targetOverride) {
		InsertionContext insertionContext = context.getInsertionContext();
		InsertionLocation adjustedInsertionLocation;
		HTMLLeaf target = targetOverride != null ? targetOverride : getCurrentNode(context);
		if (insertionContext.fosterParentingEnabled() && isTableRelatedTarget(target)) {
			// TODO
			adjustedInsertionLocation = new InsertionLocation(target, target.getNumChildren());
		} else {
			adjustedInsertionLocation = new InsertionLocation(target, target.getNumChildren());
		}
		
		// TODO: Template contents
		
		return adjustedInsertionLocation;
	}
	
	public static HTMLElementLeaf createAnElementForToken(HTMLParserContext context, StartTagToken token, String givenNamespace, HTMLLeaf intendedParent) {
		if (context.getSpeculativeHTMLParser() != null) {
			// TODO
		} else {
			// TODO
		}
		
		HTMLDocumentLeaf document = intendedParent.getNodeDocument();
		String localName = token.getName();
		String is = token.getAttributeValue("is");
		// TODO: Scripting integration
		HTMLElementLeaf element = context
			.getTreeBuilder()
			.createElement(document, localName, givenNamespace, null, is, false);
		for (TokenAttribute attribute: token.getAttributes()) {
			HTMLAttribute htmlAttribute = new HTMLAttribute(
				attribute.name().toString(),
				attribute.value().toString());
			element.appendAttribute(htmlAttribute);
		}
		
		// TODO: A lot more stuff
		
		return element;
	}
	
	public static HTMLElementLeaf insertAForeignElement(HTMLParserContext context, StartTagToken token, String namespace) {
		InsertionLocation adjustedInsertionLocation = getAppropriatePlaceForInsertingNode(context, null);
		HTMLLeaf parent = adjustedInsertionLocation.parent();
		HTMLElementLeaf element = createAnElementForToken(context, token, namespace, parent);
		if (parent.canInsert(element)) {
			// TODO
			parent.insert(adjustedInsertionLocation.location(), element);
		}
		context
			.getInsertionContext()
			.getOpenElementStack()
			.push(element);
		return element;
	}
	
	public static HTMLElementLeaf insertHTMLElement(HTMLParserContext context, StartTagToken token) {
		return insertAForeignElement(context, token, HTML_NAMESPACE);
	}
	
	public static void insertCharacter(HTMLParserContext context, CharacterToken token) {
		insertCharacter(context, token, token.getCharacter());
	}
	
	public static void insertCharacter(HTMLParserContext context, CharacterToken token, int[] data) {
		for (int ch: data) {
			insertCharacter(context, token, ch);
		}
	}
	
	public static void insertCharacter(HTMLParserContext context, CharacterToken token, int ch) {
		InsertionLocation adjustedInsertionLocation = getAppropriatePlaceForInsertingNode(context, null);
		HTMLLeaf parent = adjustedInsertionLocation.parent();
		if (parent instanceof HTMLDocumentLeaf) {
			return;
		}
		int position = adjustedInsertionLocation.location();
		if (position > 0 && parent.getLeaf(position - 1) instanceof HTMLTextLeaf textLeaf) {
			textLeaf.appendData(ch);
		} else {
			HTMLTextLeaf textLeaf = context
				.getTreeBuilder()
				.createText(parent.getNodeDocument(), ch);
			parent.insert(position, textLeaf);
		}
	}

	public static void insertAComment(HTMLParserContext context, CommentToken token, InsertionLocation position) {
		String data = token.getValue();
		InsertionLocation adjustedInsertionLocation = position != null ?
			position :
			getAppropriatePlaceForInsertingNode(context, null);
		HTMLLeaf parent = adjustedInsertionLocation.parent();
		HTMLCommentLeaf leaf = context
			.getTreeBuilder()
			.createComment(parent.getNodeDocument(), data);
		parent
			.insert(adjustedInsertionLocation.location(), leaf);
	}

	public static void parseGenericRawText(HTMLParserContext context, StartTagToken token) {
		insertHTMLElement(context, token);
		TokenizeState nextState = context.getTokenizeState(RawtextState.class);
		InsertionContext insertionContext = context.getInsertionContext();
		insertionContext.setNextTokenizeStateOverride(nextState);
		insertionContext.setOriginalInsertionMode(context.getCurrentInsertionMode());
		InsertionMode nextMode = context.getInsertionMode(TextInsertionMode.class);
		context.setCurrentInsertionMode(nextMode);
	}

	public static void parseGenericRCDATA(HTMLParserContext context, StartTagToken token) {
		insertHTMLElement(context, token);
		TokenizeState nextState = context.getTokenizeState(RCDataState.class);
		InsertionContext insertionContext = context.getInsertionContext();
		insertionContext.setNextTokenizeStateOverride(nextState);
		insertionContext.setOriginalInsertionMode(context.getCurrentInsertionMode());
		InsertionMode nextMode = context.getInsertionMode(TextInsertionMode.class);
		context.setCurrentInsertionMode(nextMode);
	}
	
	public static void generateImpliedEndTags(HTMLParserContext context, String... exceptions) {
		Stack<HTMLLeaf> stack = context
			.getInsertionContext()
			.getOpenElementStack();
		
		while (true) {
			HTMLLeaf currentNode = stack.peek();
			if (
				!(currentNode instanceof HTMLElementLeaf) ||
				!((HTMLElementLeaf) currentNode).getNamespace().equals(HTML_NAMESPACE)
			) {
				return;
			}
			String name = ((HTMLElementLeaf) currentNode).getLocalName();
			if (
				stringIs(name, exceptions) ||
				!stringIs(name, "dd", "dt", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc")
			) {
				return;
			}
			StackUtil.pop(context);
		}
	}

	public static InsertionLocation getDocumentTailInsertionLocation(HTMLParserContext context) {
		HTMLLeaf leaf = context.getTreeBuilder().getDocument();
		return new InsertionLocation(leaf, leaf.getNumChildren());
	}

	public static void reconstructActiveFormattingElements(HTMLParserContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public static HTMLElementLeaf createElement(HTMLParserContext context, String name, HTMLDocumentLeaf document) {
		return context
			.getTreeBuilder()
			.createElement(document, name, HTML_NAMESPACE, null, null, false);
	}
	
	private static HTMLLeaf getCurrentNode(HTMLParserContext context) {
		return context
			.getInsertionContext()
			.getOpenElementStack()
			.peek();
	}
	
	private static boolean isTableRelatedTarget(HTMLLeaf target) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static boolean stringIs(String name, String... names) {
		for (String matchName: names) {
			if (matchName.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
}
