package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;

public class ComputedChildrenHelper {
	private final List<WebComponentUI> computedChildren;
	private final WebComponent component;
	
	private WebComponentUI[] computedChildrenAsArray;
	
	public ComputedChildrenHelper(WebComponent component) {
		this.computedChildren = new LinkedList<>();
		this.component = component;
	}
	
	public void recompute(Function<WebComponent, WebComponentUI> func) {
		int[] found = {-1};
		for (WebComponent child: component.getChildren()) {
			found[0]++; 
			int slot = found[0];
			if (containsUIFor(child, computedChildren, found)) {
				for (int i = slot; i < found[0]; i++) {
					computedChildren.remove(slot);
				}
				found[0] = slot;
			} else {
				WebComponentUI ui = func.apply(child);
				computedChildren.add(slot, ui);
			}
		}
		for (int i = found[0]+1; i < computedChildren.size(); i++) {
			computedChildren.remove(i);
		}
		
		this.computedChildrenAsArray = computedChildren.toArray(new WebComponentUI[computedChildren.size()]);
	}

	private boolean containsUIFor(WebComponent target, List<WebComponentUI> source, int[] index) {
		for (int i = index[0]; i < source.size(); i++) {
			if (source.get(i).getComponent() == target) {
				index[0] = i;
				return true;
			}
		}
		return false;
	}

	public WebComponentUI[] getChildren() {
		return computedChildrenAsArray;
	}
}