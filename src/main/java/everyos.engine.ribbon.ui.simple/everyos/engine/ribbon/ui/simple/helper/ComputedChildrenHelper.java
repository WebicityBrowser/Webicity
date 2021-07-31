package everyos.engine.ribbon.ui.simple.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;

public class ComputedChildrenHelper {
	private List<ComponentUI> computedChildren;
	private Component component;
	
	public ComputedChildrenHelper(Component component) {
		this.computedChildren = new LinkedList<>();
		this.component = component;
	}
	
	public void recompute(Function<Component, ComponentUI> func) {
		int[] found = {-1};
		for (Component child: component.getChildren()) {
			found[0]++; 
			int slot = found[0];
			if (containsUIFor(child, computedChildren, found)) {
				for (int i = slot; i < found[0]; i++) {
					ComponentUI deleted = computedChildren.get(i);
					deleted.getComponent().unbind(deleted);
					computedChildren.remove(i);
				}
				found[0] = slot;
			} else {
				ComponentUI ui = func.apply(child);
				child.bind(ui);
				computedChildren.add(slot, ui);
			}
		}
		// TODO: Remove remaining things
	}

	private boolean containsUIFor(Component target, List<ComponentUI> source, int[] index) {
		for (int i = index[0]; i < source.size(); i++) {
			if (source.get(i).getComponent() == target) {
				index[0] = i;
				return true;
			}
		}
		return false;
	}

	public ComponentUI[] getChildren() {
		return computedChildren.toArray(new ComponentUI[computedChildren.size()]);
	}
}
