package com.github.webicitybrowser.threadyweb.tree.imp;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.graphical.cache.MappingCache;
import com.github.webicitybrowser.thready.gui.graphical.cache.imp.MappingCacheImp;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebComponentFactory;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;

public class ElementComponentImp extends BaseWebComponent implements ElementComponent {

	private final Element element;
	
	private final MappingCache<Node, WebComponent> componentCache = new MappingCacheImp<>(WebComponent[]::new, component -> component.getNode());

	public ElementComponentImp(Element element) {
		this.element = element;
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
		Node[] children = element.getChildNodes().toArray();
		componentCache.recompute(children, WebComponentFactory::createWebComponent);
		return componentCache.getComputedMappings();
	}

}
