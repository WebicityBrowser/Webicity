package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.cache.imp;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.cache.ChildComponentUICache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.gui.tree.core.UINode;

public class ChildComponentUICacheImp implements ChildComponentUICache {
	
	// TODO: Create relevant tests

	// Built-in linked lists do not give us access to the node
	private final CacheNode<ComponentUI> uiCache = new CacheNode<>(null);
	
	private ComponentUI[] uiCacheAsArray = new ComponentUI[0];
	
	@Override
	public void recompute(UINode[] children, Function<Component, ComponentUI> uiGenerator) {
		ensureCacheContainsCurrentUIs(children, uiGenerator);
		this.uiCacheAsArray = saveCacheToArray(new ComponentUI[children.length]);
	}

	@Override
	public ComponentUI[] getChildrenUI() {
		return uiCacheAsArray;
	}
	
	private void ensureCacheContainsCurrentUIs(UINode[] children, Function<Component, ComponentUI> uiGenerator) {
		CacheNode<ComponentUI> lastNode = uiCache;
		
		for (UINode child: children) {
			lastNode = ensureCacheContainsUINodeUIs(child, uiGenerator, lastNode);
		}
		
		lastNode.setNext(null);
	}

	private CacheNode<ComponentUI> ensureCacheContainsUINodeUIs(
		UINode child, Function<Component, ComponentUI> uiGenerator, CacheNode<ComponentUI> lastNode
	) {
		if (child instanceof Component component) {
			return ensureCacheContainsComponentUI(component, uiGenerator, lastNode);
		} else {
			// TODO: Support other types when they are created
			throw new UnsupportedOperationException("Not implemented");
		}
	}

	private CacheNode<ComponentUI> ensureCacheContainsComponentUI(
		Component child, Function<Component, ComponentUI> uiGenerator, CacheNode<ComponentUI> lastNode
	) {
		CacheNode<ComponentUI> nextNode = searchForUI(child, lastNode);
		return nextNode != null ?
			removeCachedUIsBetween(lastNode, nextNode) :
			generateNextUI(child, uiGenerator, lastNode);
	}

	private CacheNode<ComponentUI> removeCachedUIsBetween(CacheNode<ComponentUI> lastNode, CacheNode<ComponentUI> nextNode) {
		CacheNode<ComponentUI> currentNode = lastNode;
		while (currentNode.getNext() != null && currentNode.getNext() != nextNode) {
			currentNode = currentNode.getNext();
		}
			
		lastNode.setNext(nextNode);
		
		return nextNode;
	}
	
	private CacheNode<ComponentUI> generateNextUI(
		Component child, Function<Component, ComponentUI> uiGenerator, CacheNode<ComponentUI> lastNode
	) {
		ComponentUI ui = uiGenerator.apply(child);
		lastNode.setNext(new CacheNode<>(ui));
		
		return lastNode.getNext();
	}

	private CacheNode<ComponentUI> searchForUI(Component uiComponent, CacheNode<ComponentUI> startNode) {
		CacheNode<ComponentUI> currentNode = startNode.getNext();
		while (currentNode != null) {
			if (currentNode.getValue().getComponent() == uiComponent) {
				return currentNode;
			}
			currentNode = currentNode.getNext();
		}
		
		return null;
	}
	
	private ComponentUI[] saveCacheToArray(ComponentUI[] componentUIs) {
		CacheNode<ComponentUI> nextNode = uiCache.getNext();
		for (int i = 0; nextNode != null; i++) {
			componentUIs[i] = nextNode.getValue();
			nextNode = nextNode.getNext();
		}
		
		return componentUIs;
	}
	
}
