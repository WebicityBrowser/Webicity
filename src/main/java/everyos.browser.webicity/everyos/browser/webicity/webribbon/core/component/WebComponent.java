package everyos.browser.webicity.webribbon.core.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;
import everyos.browser.webicity.webribbon.core.cssom.Attribute;

public class WebComponent { //TODO: Code will be moved to WebUI
	@SuppressWarnings("unused")
	private Map<Attribute, Object> attributes;
	private Node node;
	private List<WebComponent> children;
	
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
				if (c!=null) children.add(c);
			}
		}
		return this.children.toArray(new WebComponent[this.children.size()]);
	}

	public Node getNode() {
		return node;
	}
}
