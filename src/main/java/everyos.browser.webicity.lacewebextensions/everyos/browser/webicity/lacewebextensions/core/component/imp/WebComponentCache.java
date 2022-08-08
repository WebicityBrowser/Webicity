package everyos.browser.webicity.lacewebextensions.core.component.imp;

import java.util.LinkedList;
import java.util.List;

import com.github.webicity.lace.core.component.Component;

import everyos.browser.spec.javadom.intf.Node;
import everyos.browser.spec.javadom.intf.NodeList;
import everyos.browser.webicity.lacewebextensions.core.component.WebComponent;

public class WebComponentCache {
	
	private final List<WebComponent> childrenComponents;
	private final WebComponentFactory componentFactory;
	
	private List<Component> childrenComponentSnapshot;
	
	public WebComponentCache() {
		this.childrenComponents = new LinkedList<>();
		this.componentFactory = new WebComponentFactory();
	}
	
	public List<Component> getComputedChildren() {
		return childrenComponentSnapshot;
	}
	
	public void setChildren(NodeList children) {
		updateCache(children);
	}
	
	private void updateCache(NodeList children) {
		int insertionIndex = 0;
		for (Node node: children) {
			int scanIndex = searchForNodeIndex(node, insertionIndex);
			if (scanIndex == -1) {
				WebComponent child = componentFactory.createComponentFor(node);
				if (child == null) {
					continue;
				}
				childrenComponents.add(insertionIndex, child);
			} else {
				removeChildrenUIBetweenIndices(insertionIndex, scanIndex);
			}
			insertionIndex++;
		}
		
		removeChildrenUIAfterIndex(insertionIndex);
		
		childrenComponentSnapshot = List.copyOf(childrenComponents);
	}

	private int searchForNodeIndex(Node node, int offset) {
		for (int i = offset; i < childrenComponents.size(); i++) {
			if (childrenComponents.get(i).getNode() == node) {
				return i;
			}
		}
		
		return -1;
	}
	
	private void removeChildrenUIBetweenIndices(int startIndex, int stopIndex) {
		for (int i = stopIndex - 1; i > startIndex ; i++) {
			childrenComponents.remove(i);
		}
	}
	
	private void removeChildrenUIAfterIndex(int index) {
		removeChildrenUIBetweenIndices(index, childrenComponents.size());
	}

}
