package everyos.engine.ribbon.components.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public class ContainerComponent implements Component {
	
	private final ArrayList<Component> children;
	private final List<ComponentUI> boundObservers;
	private final Map<Class<? extends ComponentUI>, List<UIDirectiveWrapper>> directives;
	
	public ContainerComponent() {
		this.children = new ArrayList<Component>();
		this.boundObservers = new ArrayList<>();
		this.directives = new HashMap<>();
	}
	
	/**
	 * Send an instruction to the L&F
	 * @param uicls A class representing which type of ComponentUIs should receive this directive
	 * @param directive The directive to be sent
	 * @return Chain-able self
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Component directive(Class<? extends ComponentUI> uicls, UIDirectiveWrapper directive) {
		List<UIDirectiveWrapper> dirs = directives.computeIfAbsent(uicls.getClass().cast(ComponentUI.class), c->new ArrayList<UIDirectiveWrapper>());
		for (int i = dirs.size()-1; i >= 0; i--) {
			UIDirectiveWrapper dir = dirs.get(i);
			if (dir.getClass().isAssignableFrom(uicls.getClass())) {
				dirs.remove(i);
			}
		}
		dirs.add(directive);
		for (ComponentUI ui: boundObservers.toArray(new ComponentUI[boundObservers.size()])) {
			if (uicls.isAssignableFrom(ui.getClass())) {
				ui.directive(directive.getDirective());
				ui.invalidate(directive.getPipelineHint());
			}
		}
		
		return this;
	}
	
	/**
	 * Sends a UIDirective to all applicable UIs
	 * @param directive The directive to be sent
	 * @return Chainable self
	 */
	@Override
	public ContainerComponent directive(UIDirectiveWrapper directive) {
		for (Class<? extends ComponentUI> cls: directive.getAffectedUIs()) {
			directive(cls, directive);
		}
		
		return this;
	}
	
	/**
	 * Get all UI directives this component holds, applicable to a given class
	 * @param cls The class to get applicable UI directives of
	 * @return The applicable UI directives
	 */
	@Override
	public <T extends ComponentUI> UIDirectiveWrapper[] getDirectives(Class<T> cls) {
		ArrayList<UIDirectiveWrapper> matches = new ArrayList<>();
		Class<?> clz = cls;
		while (ComponentUI.class.isAssignableFrom(clz)) {
			if (directives.containsKey(clz)) {
				directives.get(clz).forEach(a -> matches.add(a));
			}
			
			//Interfaces are not returned by .getSuperclass, so we have this primitive code that needs fixed
			for (Class<?> c: clz.getInterfaces()) {
				if (ComponentUI.class.isAssignableFrom(c) && directives.containsKey(c)) {
					directives.get(c).forEach(a -> matches.add(a));
				}
			}
			clz = clz.getSuperclass();
		}
		return matches.toArray(new UIDirectiveWrapper[matches.size()]);
	}
	
	@Override
	public void addChild(Component component) {
		children.add(component);
		invalidate(InvalidationLevel.RENDER);
	}
	
	@Override
	public void addChild(int pos, Component component) {
		children.add(pos, component);
		invalidate(InvalidationLevel.RENDER);
	}

	@Override
	public void removeChild(Component component) {
		if (children.remove(component)) {
			invalidate(InvalidationLevel.RENDER);
		}
	}
	
	@Override
	public void removeChild(int i) {
		children.remove(i);
		invalidate(InvalidationLevel.RENDER);
	}

	@Override
	public Component children(Component[] children) {
		this.children.clear();
		this.children.ensureCapacity(children.length);
		this.children.trimToSize();
		for (Component component: children) {
			addChild(component);
		}
		
		return this;
	}
	
	@Override
	public Component[] getChildren() {
		return children.toArray(new ContainerComponent[children.size()]);
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		for (ComponentUI ui: boundObservers.toArray(new ComponentUI[boundObservers.size()])) {
			ui.invalidate(level);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Component> T casted() {
		return (T) this;
	}
	
	/**
	 * Binds a ComponentUI to this component, allowing it to receive events from this component
	 * @param ui The ComponentUI to be bound
	 */
	@Override
	public void bind(ComponentUI ui) {
		boundObservers.add(ui);
		for (UIDirectiveWrapper dir: getDirectives(ui.getClass())) {
			ui.directive(dir.getDirective());
		}
	}
	
	@Override
	public void unbind(ComponentUI ui) {
		boundObservers.remove(ui);
	}

	@Override
	public Component[] query(Function<Component, Boolean> query) {
		List<Component> components = new LinkedList<>();
		List<Component> matches = new ArrayList<>();
		
		components.add(this);
		while(components.size() > 0) {
			Component cur = components.get(0);
			if (query.apply(cur)) {
				matches.add(cur);
			}
			for (Component child: cur.getChildren()) {
				components.add(child);
			}
			components.remove(0);
		}
		
		return matches.toArray(new Component[matches.size()]);
	}
	
}