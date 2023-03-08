package everyos.parser.portalhtml.emit.util;

import everyos.parser.portalhtml.context.HTMLParserContext;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public final class StackUtil {

	private StackUtil() {}
	
	public static HTMLLeaf pop(HTMLParserContext context) {
		return context
			.getInsertionContext()
			.getOpenElementStack()
			.pop();
	}
	
	public static HTMLLeaf getCurrentNode(HTMLParserContext context) {
		return context
			.getInsertionContext()
			.getOpenElementStack()
			.peek();
	}

	public static boolean hasElementInButtonScope(HTMLParserContext context, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void popUntilHTMLElement(HTMLParserContext context, String name) {
		while (!LeafUtil.isHTMLElementWithName(pop(context), name));
	}
	
}
