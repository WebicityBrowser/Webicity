package everyos.browser.webicity.renderer.html.dom;

import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	public ArrayList<Node> children = new ArrayList<>();
	public Document nodeDocument;
	
	public HashMap<String, Object> fields = new HashMap<>();
	protected Node parent;
	
	public void appendChild(Node child) {
		//if (child==null) return;
		//System.out.println(this+":"+child);
		children.add(child);
		child.parent = this;
		child.nodeDocument = this.nodeDocument;
	}

	public void setField(String key, Object value) {
		fields.put(key, value);
	}
	public Object getFieldOrDefault(String key, Object value) {
		return fields.getOrDefault(key, value);
	}
	
	public Node getParent() {
		return parent;
	}

	public Node lastChild() {
		if (children.isEmpty()) return null;
		return children.get(children.size()-1);
	}
}
