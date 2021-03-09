package everyos.browser.javadom.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;

//TODO: I skipped all of the spec before this
public class JDNode extends JDEventTarget implements Node {
	private Document nodeDocument;
	private List<Node> children = new ArrayList<Node>(4);
	
	protected JDNode(Document nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	//TODO: Include constants?
	@Override
	public short getNodeType() {
		return 0; // Um, no proper case for this?
	}
	@Override
	public String getNodeName() {
		return "null";
	}
	@Override
	public String getBaseURL() {
		//return getNodeDocument()
		//TODO
		return null;
	}
	
	@SuppressWarnings("unused")
	private Document getNodeDocument() {
		return nodeDocument;
	}
	
	@Override
	public Node getRootNode(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node appendChild(Node child) {
		return preInsert(child, null);
	}

	@Override
	public Node getParentNode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node getLastChild() {
		if (children.isEmpty()) return null;
		return children.get(children.size()-1);
	}
	@Override
	public Document getOwnerDocument() {
		return nodeDocument;
	}
	@Override
	public NodeList getChildNodes() {
		return new JDNodeList(children);
	}
	@Override
	public Node getNextSibling() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void setNodeDocument(JDDocument nodeDocument) {
		this.nodeDocument = nodeDocument;
	}
	
	private Node preInsert(Node node, Node child) {
		//TODO: Ensure validity
		Node referenceChild = child;
		if (referenceChild == node) {
			referenceChild = node.getNextSibling();
		}
		
		insert(node, child, false);
		
		return node;
	}

	private void insert(Node node, Node child, boolean suppressObservers) {
		//TODO: Actually implement
		children.add(node);
	}
}
