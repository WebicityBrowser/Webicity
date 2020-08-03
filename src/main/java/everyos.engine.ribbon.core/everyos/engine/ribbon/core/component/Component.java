package everyos.engine.ribbon.core.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public class Component {
	public Component parent;
	protected ArrayList<Component> children;
	protected ArrayList<ComponentUI> bound;
	protected HashMap<String, Object> tags;
	protected HashMap<Class<? extends ComponentUI>, ArrayList<UIDirectiveWrapper>> directives;
	
	@Deprecated public Component attribute(String attr, Object o) { return this; }
	@Deprecated public Component attribute(String attr, Runnable o) { return this; }
	
	public Component(Component parent) {
		children = new ArrayList<Component>();
		tags = new HashMap<>();
		bound = new ArrayList<>();
		directives = new HashMap<>();
		if (parent!=null) this.setParent(parent);
	}
	
	/**
	 * 
	 * @param uicls A class representing which type of ComponentUIs should receive this directive
	 * @param directive The directive to be sent
	 * @return Chainable self
	 */
	@SuppressWarnings("unchecked")
	public Component directive(Class<? extends ComponentUI> uicls, UIDirectiveWrapper directive) {
		ArrayList<UIDirectiveWrapper> dirs = directives.computeIfAbsent(uicls.getClass().cast(ComponentUI.class), c->new ArrayList<UIDirectiveWrapper>());
		for (int i = dirs.size()-1; i>=0; i--) {
			UIDirectiveWrapper dir = dirs.get(i);
			if (dir.getClass().isAssignableFrom(uicls.getClass())) dirs.remove(i);
		}
		dirs.add(directive);
		for (ComponentUI ui: bound.toArray(new ComponentUI[bound.size()])) {
			if (uicls.isAssignableFrom(ui.getClass()))
			ui.directive(directive.getDirective());
		}
		return this;
	}
	
	/**
	 * Sends a UIDirective to all applicable UIs
	 * @param directive The directive to be sent
	 * @return Chainable self
	 */
	public Component directive(UIDirectiveWrapper directive) {
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
	public <T extends ComponentUI> UIDirectiveWrapper[] getDirectives(Class<T> cls) {
		ArrayList<UIDirectiveWrapper> matches = new ArrayList<>();
		Class<?> clz = cls;
		while (ComponentUI.class.isAssignableFrom(clz)) {
			if (directives.containsKey(clz)) {
				directives.get(clz).forEach(a->{
					matches.add(a);
				});
			}
			
			//Interfaces are not returned by .getSuperclass, so we have this primitive code that needs fixed
			for (Class<?> c: clz.getInterfaces()) {
				if (ComponentUI.class.isAssignableFrom(c)&&directives.containsKey(c)) {
					directives.get(c).forEach(a->{
						matches.add(a);
					});
				}
			}
			clz = clz.getSuperclass();
		}
		return matches.toArray(new UIDirectiveWrapper[matches.size()]);
	}
	
	public void addChild(Component component) {
		component.setParent(this);
	}
	public void addChild(int pos, Component component) {
		component.setParent(this, pos);
	}

	/**
	 * Change this component's parent
	 * @param parent The new parent
	 */
	public void setParent(Component parent) {
		setParent(parent, parent.children.size());
	}
	
	/**
	 * Change this component's parent
	 * @param parent The new parent
	 * @param pos The placement of this component within the new parent's children
	 */
	public void setParent(Component parent, int pos) {
		if (this.parent!=null) this.parent.delete(this);
		this.parent = parent;
		parent.children.add(pos, this);
		parent.invalidate();
	}

	public void delete() {
		if (parent!=null) parent.delete(this);
	}
	public void delete(Component component) {
		children.remove(component);
		component.parent = null;
		invalidate();
	}

	public Component children(Component[] children) {
		this.children = new ArrayList<Component>(children.length);/*Arrays.asList(children));*/
		for (Component child: children) child.setParent(this);
		return this;
	}
	public Component[] getChildren() {
		return children.toArray(new Component[children.size()]);
	}
	
	public void invalidate() {
		Component c = this;
		while (c!=null) {
			c.invalidateLocal();
			c = c.parent;
		}
	}
	protected void invalidateLocal() {
		for (ComponentUI ui: bound.toArray(new ComponentUI[bound.size()])) {
			ui.invalidate();
		}
	}
	
	public Component tag(String name, Object o) {
		tags.put(name, o);
		return this;
	}
	public Object getTag(String name) {
		return tags.get(name);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T casted(Class<T> cls) {
		return (T) this;
	}
	
	/**
	 * Binds a ComponentUI to this component, allowing it to receive events from this component
	 * @param ui The ComponentUI to be bound
	 */
	public void bind(ComponentUI ui) {
		bound.add(ui);
		for (UIDirectiveWrapper dir: getDirectives(ui.getClass())) ui.directive(dir.getDirective());
	}

	public Component[] query(Function<Component, Boolean> query) {
		ArrayList<Component> components = new ArrayList<>();
		ArrayList<Component> matches = new ArrayList<>();
		components.add(this);
		
		while(components.size()>0) {
			Component cur = components.get(0);
			if (query.apply(cur)) matches.add(cur);
			for (Component child: cur.getChildren()) components.add(child);
			components.remove(0);
		}
		
		return matches.toArray(new Component[matches.size()]);
	}
	public Component getComponentByID(String id) {
		Component[] cl = query(c->equals(c.getTag("id"), id));
		if (cl.length==0) return null;
		return cl[0];
	}

	private boolean equals(Object a, Object b) {
		return a==null?false:a.equals(b);
	}
}