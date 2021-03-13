package everyos.browser.javadom.intf;

public interface Element extends Node {
	String getTagName();
	String getLocalName();
	String getNamespaceURI();
	String getAttribute(String string);
	void setAttribute(String n, String v);
}
