package everyos.browser.webicity.webribbon.core.component;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.NodeList;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.engine.ribbon.core.event.UIEventTarget;

public class WebComponent implements UIEventTarget {
	private final HTMLRenderer renderer;
	private final Node node;
	
	private WebComponent[] childrenArray;
	
	public WebComponent(HTMLRenderer renderer, Node node) {
		this.renderer = renderer;
		this.node = node;
	}
	
	public WebComponent[] getChildren() {
		//TODO: Update this when more children are added
		
		if (this.childrenArray == null) {
			NodeList nchildren = node.getChildNodes();
			int len = (int) nchildren.getLength();
			List<WebComponent> children = new ArrayList<>(len);
			for (int i=0; i<len; i++) {
				WebComponent c = WebComponentFactory.createComponentFromNode(nchildren.item(i), renderer);
				if (c!=null) {
					children.add(c);
				}
			}
			
			this.childrenArray = children.toArray(new WebComponent[children.size()]);
		}
		
		return this.childrenArray;
	}

	public Node getNode() {
		return node;
	}

	public HTMLRenderer getRenderer() {
		return renderer;
	}
}
