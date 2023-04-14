package com.github.webicitybrowser.spiderhtml.misc;

import java.util.HashMap;

import com.github.webicitybrowser.spec.html.parse.ElementCreationOptions;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLElementLeaf;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;
import com.github.webicitybrowser.spec.infra.Namespace;
import com.github.webicitybrowser.spiderhtml.context.InsertionContext;
import com.github.webicitybrowser.spiderhtml.token.StartTagToken;

public final class InsertionLogic {

	private InsertionLogic() {}

	public static HTMLElementLeaf insertHTMLElement(InsertionContext insertionContext, StartTagToken startTagToken) {
		return insertForeignElement(insertionContext, startTagToken, Namespace.HTML_NAMESPACE);
	}

	private static HTMLElementLeaf insertForeignElement(InsertionContext insertionContext, StartTagToken startTagToken, String namespace) {
		// TODO
		InsertionLocation insertionLocation = getAppropriatePlaceForInsertingNode(insertionContext, null);
		HTMLElementLeaf element = createElementForToken(insertionContext, startTagToken, namespace, insertionLocation.parent());
		// TODO: Ensure that parent can accept more elements
		insertElement(insertionLocation, element);
		insertionContext.getOpenElementStack().push(element);
		
		return element;
	}
	
	private static void insertElement(InsertionLocation insertionLocation, HTMLLeaf element) {
		insertionLocation
			.parent()
			.insertBeforeLeaf(insertionLocation.before(), element);
	}

	public static HTMLElementLeaf createElementForToken(
		InsertionContext insertionContext, StartTagToken token, String namespace, HTMLLeaf intendedParent
	) {
		ElementCreationOptions creationOptions = new ElementCreationOptions(
			token.getName(), new HashMap<String, String>(),
			namespace, intendedParent
		);
		return insertionContext
			.getTreeBuilder()
			.createHtmlElementLeaf(creationOptions);
	}

	private static InsertionLocation getAppropriatePlaceForInsertingNode(InsertionContext insertionContext, HTMLLeaf targetOverride) {
		// TODO
		HTMLLeaf target = targetOverride != null ? targetOverride : getCurrentNode(insertionContext);
		InsertionLocation adjustedInsertionLocation = new InsertionLocation(target, null);
		
		return adjustedInsertionLocation;
	}

	private static HTMLLeaf getCurrentNode(InsertionContext insertionContext) {
		return insertionContext
			.getOpenElementStack()
			.peek();
	}
	
	
	
}
