package everyos.browser.webicity.renderer.html.dom.impl;

import java.util.ArrayList;

import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.dom.NodeList;

public class NodeImpl implements Node {
	
	private DocumentImpl nodeDocument;
	private ArrayList<Node> children = new ArrayList<>(0);
	private NodeList childrenList;
	private Node parent;

	public NodeImpl(DocumentImpl nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	///

	@Override public String getNodeName() {
		return null;
	}

	@Override public short getNodeType() {
		return -1;
	}
	
	///
	
	@Override public String getBaseURI() {
		return null;
	}
	
	@Override public boolean getIsConnected() {
		return true;
	}
	
	@Override public DocumentImpl getOwnerDocument() {
		return nodeDocument;
	}
	
	@Override public Node getRootNode() { //TODO: Arguments
		return null;
	}
	
	@Override public Node getParentNode() {
		return parent;
	}
	
	@Override public Element getParentElement() {
		return parent instanceof Element?(Element) parent:null;
	}
	
	@Override public boolean hasChildNodes() {
		return children.size()>0;
	}
	
	@Override public NodeList getChildNodes() {
		if (childrenList==null) {
			childrenList = new NodeListImpl(children);
		}
		return childrenList;
	}
	
	@Override public Node getFirstChild() {
		return hasChildNodes()?children.get(0):null;
	}

	@Override public Node getLastChild() {
		return hasChildNodes()?children.get(children.size()-1):null;
	}

	@Override public Node getPreviousSibling() {
		return null;
	}

	@Override public Node getNextSibling() {
		return null;
	}
	
	///
	
	@Override public String getNodeValue() {
		return null;
	}

	@Override public void setNodeValue(String nodeValue) {
		
	}
	
	@Override public String getTextContent() {
		return null;
	}

	@Override public void setTextContent(String textContent) {
		
	}
	
	@Override public void normalize() {
		
	}
	
	///
	
	@Override public Node cloneNode(boolean deep) {
		return null;
	}

	@Override public boolean isSameNode(Node other) {
		return other==this;
	}

	@Override public boolean isEqualNode(Node arg) {
		return false;
	}
	
	///
	
	@Override public short compareDocumentPosition(Node other) {
		return 0;
	}
	
	@Override public boolean contains(Node other) {
		return children.contains(other);
	};
	
	///
	
	@Override public String lookupPrefix(String namespaceURI) {
		return null;
	}
	
	@Override public String lookupNamespaceURI(String prefix) {
		return null;
	}
	
	@Override public boolean isDefaultNamespace(String namespaceURI) {
		return false;
	}

	///
	
	@Override public Node insertBefore(Node newChild, Node refChild) {
		return null;
	}
	
	@Override public Node appendChild(Node child) {
		//if (child==null) return;
		//System.out.println(this+":"+child);
		children.add(child);
		child.setParent(this);
		
		return child; //TODO
		//child.parent = this;
	}
	
	@Override public Node replaceChild(Node node, Node oldChild) {
		return null;
	}
	
	@Override public Node removeChild(Node oldChild) {
		oldChild.setParent(null);
		children.remove(oldChild);
		
		return oldChild; //
	}
	
	//Implementation specific
	@Override public void setParent(Node parent) {
		this.parent = parent;
	}
	
}
