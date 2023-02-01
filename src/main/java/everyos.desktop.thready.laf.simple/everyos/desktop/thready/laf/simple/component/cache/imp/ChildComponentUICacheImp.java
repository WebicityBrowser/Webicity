package everyos.desktop.thready.laf.simple.component.cache.imp;

import java.util.function.Function;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.laf.simple.component.cache.ChildComponentUICache;

public class ChildComponentUICacheImp implements ChildComponentUICache {

	// Built-in linked lists do not give us access to the node
	private final CacheNode<ComponentUI> uiCache = new CacheNode<>(null);
	
	private ComponentUI[] uiCacheAsArray = new ComponentUI[0];
	
	@Override
	public void recompute(Component[] children, Function<Component, ComponentUI> uiGenerator) {
		ensureCacheContainsCurrentUIs(children, uiGenerator);
		this.uiCacheAsArray = saveCacheToArray(new ComponentUI[children.length]);
	}

	@Override
	public ComponentUI[] getChildrenUI() {
		return uiCacheAsArray;
	}
	
	private void ensureCacheContainsCurrentUIs(Component[] children, Function<Component, ComponentUI> uiGenerator) {
		CacheNode<ComponentUI> lastNode = uiCache;
		
		for (Component child: children) {
			CacheNode<ComponentUI> nextNode = searchForUI(child, lastNode);
			if (nextNode != null) {
				lastNode = removeCachedUIsBetween(lastNode, nextNode);
			} else {
				lastNode = generateNextUI(child, uiGenerator, lastNode);
			}
		}
		
		lastNode.setNext(null);
	}

	private CacheNode<ComponentUI> removeCachedUIsBetween(CacheNode<ComponentUI> lastNode, CacheNode<ComponentUI> nextNode) {
		lastNode.setNext(nextNode);
		
		return nextNode;
	}
	
	private CacheNode<ComponentUI> generateNextUI(
		Component child, Function<Component, ComponentUI> uiGenerator, CacheNode<ComponentUI> lastNode
	) {
		ComponentUI ui = uiGenerator.apply(child);
		child.bindUI(ui);
		lastNode.setNext(new CacheNode<ComponentUI>(ui));
		
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
