package everyos.browser.spec.javadom.intf;

public interface Node extends EventTarget {
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
	
	//TODO Should this be an actual method?
	void setParent(Node node);
}
