package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebComponentFactory;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementComponentImp extends BaseWebComponent implements ElementComponent {

	private final Element element;
	private final WebComponentContext componentContext;
	
	private final MappingCache<Node, WebComponent> componentCache = new MappingCacheImp<>(WebComponent[]::new, component -> component.getNode());

	public ElementComponentImp(Element element, WebComponentContext componentContext) {
		this.element = element;
		this.componentContext = componentContext;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return ElementComponent.class;
	}
	
	@Override
	public Node getNode() {
		return this.element;
	}

	@Override
	public WebComponent[] getChildren() {
		Node[] children = filterChildren(element.getChildNodes());
		componentCache.recompute(children, child -> WebComponentFactory.createWebComponent(child, componentContext));
		return componentCache.getComputedMappings();
	}

	private Node[] filterChildren(NodeList childNodes) {
		int childrenLength = childNodes.getLength();
		Node[] filtered = new Node[childrenLength];
		int i = 0;
		for (Node childNode: childNodes) {
			if (childNode instanceof Text || childNode instanceof Element) {
				filtered[i++] = childNode;
			}
		}
		
		if (i == childrenLength) {
			return filtered;
		}
		
		Node[] resized = new Node[i];
		System.arraycopy(filtered, 0, resized, 0, i);
		
		return resized;
	}

}
