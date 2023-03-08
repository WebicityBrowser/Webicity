package everyos.parser.portalhtml.emit.util;

import everyos.parser.portalhtml.tree.HTMLElementLeaf;
import everyos.parser.portalhtml.tree.HTMLLeaf;

public final class LeafUtil {

	private LeafUtil() {}
	
	public static boolean isHTMLElementWithName(HTMLLeaf leaf, String name) {
		return
			leaf instanceof HTMLElementLeaf &&
			((HTMLElementLeaf) leaf).getNamespace().equals(TreeUtil.HTML_NAMESPACE) &&
			((HTMLElementLeaf) leaf).getLocalName().equals(name);
	}
	
	public static boolean isHTMLElementWithOneOfName(HTMLLeaf leaf, String... names) {
		return
			leaf instanceof HTMLElementLeaf &&
			((HTMLElementLeaf) leaf).getNamespace().equals(TreeUtil.HTML_NAMESPACE) &&
			stringIs(((HTMLElementLeaf) leaf).getLocalName(), names);
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
