package everyos.browser.spec.javadom.intf;

public interface Element extends Node {
	String getNamespaceURI();
	String getLocalName();
	String getTagName();
	
	String getId();
	DOMTokenList getClassList();
	
	String getAttribute(String string);
	void setAttribute(String n, String v);
}
