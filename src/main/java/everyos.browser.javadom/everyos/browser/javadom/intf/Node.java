package everyos.browser.javadom.intf;

public interface Node {
	Node getRootNode(Object o);
	Node appendChild(Node child);
	Node getParentNode();
	Node getLastChild();
	Document getOwnerDocument();
	NodeList getChildNodes();
	Node getNextSibling();
	short getNodeType();
	String getNodeName();
	String getBaseURL();
	
	String getChildTextContent();
}
