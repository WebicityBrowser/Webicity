package everyos.browser.webicity.webribbon.core.component;

import java.util.ArrayList;
import java.util.HashMap;

import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;
import everyos.browser.webicity.webribbon.core.cssom.Attribute;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;

public class WebComponent { //TODO: Code will be moved to WebUI
	protected HashMap<Attribute, Object> attributes;
	protected Node node;
	protected SizePosGroup bounds;
	protected ArrayList<WebComponent> children;
	protected WebComponent parent;
	
	public WebComponent(Node node) {
		this.node = node;
	}
	
	public WebComponent[] getChildren() {
		if (this.children == null) {
			NodeList nchildren = node.getChildNodes();
			int len = (int) nchildren.getLength();
			this.children = new ArrayList<>(len);
			for (int i=0; i<len; i++) {
				WebComponent c = WebComponentFactory.createComponentFromNode(nchildren.item(i));
				c.setParent(this);
				if (c!=null) children.add(c);
			}
		}
		return this.children.toArray(new WebComponent[this.children.size()]);
	}
	private void setParent(WebComponent parent) {
		this.parent = parent;
	}

	public void bind(WebComponentUI ui) {
		// TODO Auto-generated method stub
		
	}

	public Node getNode() {
		return node;
	}

	/*public WebComponent[] parent() {
		return null;
	}*/
}
