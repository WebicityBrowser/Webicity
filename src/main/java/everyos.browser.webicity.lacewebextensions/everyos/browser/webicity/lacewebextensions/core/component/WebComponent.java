package everyos.browser.webicity.lacewebextensions.core.component;

import java.util.List;

import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.imputils.ComponentBase;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.webicity.lacewebextensions.core.component.imp.WebComponentCache;

public class WebComponent extends ComponentBase {

	private final Node node;
	private final WebComponentCache childrenCache;

	public WebComponent(Node node) {
		this.node = node;
		this.childrenCache = new WebComponentCache();
		childrenCache.setChildren(node.getChildNodes());
	}
	
	public Node getNode() {
		return node;
	}

	public List<Component> getChildren() {
		return childrenCache.getComputedChildren();
	};
	
}
