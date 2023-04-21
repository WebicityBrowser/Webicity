package com.github.webicitybrowser.spiderhtml.misc;

import java.util.HashMap;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.context.SharedContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken.StartTagAttribute;

public final class InsertionLogic {

	private InsertionLogic() {}

	public static HTMLElement insertHTMLElement(InsertionContext insertionContext, StartTagToken startTagToken) {
		return insertForeignElement(insertionContext, startTagToken, Namespace.HTML_NAMESPACE);
	}
	
	public static void insertCharacters(SharedContext context, InsertionContext insertionContext, int[] ch) {
		String text = codePointsToString(ch);
		InsertionLocation adjustedInsertionLocation = getAppropriatePlaceForInsertingNode(insertionContext, null);
		if (adjustedInsertionLocation.parent() instanceof Document) {
			return;
		}
		Node precedingNode = getLeafBefore(adjustedInsertionLocation);
		if (precedingNode != null && precedingNode instanceof Text textNode) {
			textNode.appendData(text);
		} else {
			Text textNode = insertionContext.getTreeBuilder().createTextNode();
			textNode.appendData(text);
			insertNode(adjustedInsertionLocation, textNode);
		}
	}

	private static String codePointsToString(int[] chars) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int ch: chars) {
			stringBuilder.appendCodePoint(ch);
		}
		return stringBuilder.toString();
	}

	private static HTMLElement insertForeignElement(InsertionContext insertionContext, StartTagToken startTagToken, String namespace) {
		// TODO
		InsertionLocation insertionLocation = getAppropriatePlaceForInsertingNode(insertionContext, null);
		HTMLElement element = createElementForToken(insertionContext, startTagToken, namespace, insertionLocation.parent());
		// TODO: Ensure that parent can accept more elements
		insertNode(insertionLocation, element);
		insertionContext.getOpenElementStack().push(element);
		
		return element;
	}
	
	private static void insertNode(InsertionLocation insertionLocation, Node node) {
		insertionLocation
			.parent()
			.insertBefore(insertionLocation.before(), node);
	}

	public static HTMLElement createElementForToken(
		InsertionContext insertionContext, StartTagToken token, String namespace, Node intendedParent
	) {
		ElementCreationOptions creationOptions = new ElementCreationOptions(
			token.getName(), new HashMap<String, String>(),
			namespace, intendedParent
		);
		HTMLElement element = insertionContext
			.getTreeBuilder()
			.createHtmlElement(creationOptions);
		for (StartTagAttribute attribute: token.getAttributes()) {
			element.setAttribute(attribute.name(), attribute.value());
		}
		
		return element;
	}

	private static InsertionLocation getAppropriatePlaceForInsertingNode(InsertionContext insertionContext, Node targetOverride) {
		// TODO
		Node target = targetOverride != null ? targetOverride : getCurrentNode(insertionContext);
		InsertionLocation adjustedInsertionLocation = new InsertionLocation(target, null);
		
		return adjustedInsertionLocation;
	}

	private static Node getCurrentNode(InsertionContext insertionContext) {
		return insertionContext
			.getOpenElementStack()
			.peek();
	}
	
	private static Node getLeafBefore(InsertionLocation location) {
		return location.before() != null ?
			location.before().getPreviousSibling() :
			location.parent().getLastChild();
	}
	
}
