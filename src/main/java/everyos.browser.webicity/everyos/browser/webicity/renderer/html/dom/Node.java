package everyos.browser.webicity.renderer.html.dom;

import everyos.browser.webicity.renderer.html.dom.impl.DocumentImpl;

public interface Node {
	String getNodeName();
	short getNodeType();
	
	String getBaseURI();
	boolean getIsConnected();
	DocumentImpl getOwnerDocument();
	Node getRootNode(); //TODO: Dictionary
	Node getParentNode();
	Element getParentElement();
	boolean hasChildNodes();
	NodeList getChildNodes();
	Node getFirstChild();
	Node getLastChild();
	Node getPreviousSibling();
	Node getNextSibling();
	
	String getNodeValue();
	void setNodeValue(String nodeValue);
	String getTextContent();
	void setTextContent(String textContent);
	void normalize();
	
	Node cloneNode(boolean deep);
	boolean isSameNode(Node other);
	boolean isEqualNode(Node arg);
	
	short compareDocumentPosition(Node other);
	boolean contains(Node other);
	
	String lookupPrefix(String namespaceURI);
	String lookupNamespaceURI(String prefix);
	boolean isDefaultNamespace(String namespaceURI);
	
	Node insertBefore(Node newChild, Node refChild);
	Node appendChild(Node child);
	Node replaceChild(Node newChild, Node oldChild);
	Node removeChild(Node oldChild);
	
	//Implementation Specific
	void setParent(Node parent);
}