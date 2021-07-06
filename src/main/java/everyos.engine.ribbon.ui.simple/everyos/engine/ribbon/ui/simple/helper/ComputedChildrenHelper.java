package everyos.engine.ribbon.ui.simple.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;

public class ComputedChildrenHelper {
	private List<ComponentUI> computedChildren;
	
	public ComputedChildrenHelper(Component component, Function<Component, ComponentUI> func) {
		this.computedChildren = new LinkedList<>();
		for (Component child: component.getChildren()) {
			child.unbindAll();
			ComponentUI ui = func.apply(child);
			child.bind(ui);
			computedChildren.add(ui);
		}
		
	}

	public ComponentUI[] getChildren() {
		return computedChildren.toArray(new ComponentUI[computedChildren.size()]);
	}
}
