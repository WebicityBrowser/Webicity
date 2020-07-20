package everyos.engine.ribbon.graphics.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import everyos.engine.ribbon.graphics.Renderer;
import everyos.engine.ribbon.graphics.ui.ComponentUI;
import everyos.engine.ribbon.graphics.ui.DrawData;
import everyos.engine.ribbon.graphics.ui.UIManager;
import everyos.engine.ribbon.input.MouseBinding;
import everyos.engine.ribbon.shape.SizePosGroup;

public class Component {
	public Component parent;
	protected ArrayList<Component> children;
	protected HashMap<String, Object> attributes;
	protected UIManager oldUIManager;
	protected UIManager uimanager;
	protected ComponentUI ui;
	
	public Component(Component parent) {
		children = new ArrayList<Component>();
		attributes = new HashMap<String, Object>();
		if (parent!=null) this.setParent(parent);
	}
	
	public UIManager getUIManager() {
		return this.uimanager!=null?this.uimanager:
			parent!=null?parent.getUIManager():null;
	}
	public void setUIManager(UIManager manager) {
		this.uimanager = manager;
	}
	
	public ComponentUI getUI() { //TODO: setUI
		UIManager newManager = getUIManager();
		if (newManager!=oldUIManager||ui==null) {
			UIManager manager = getUIManager();
			if (manager==null) return null;
			ui = manager.get(this);
		}
		oldUIManager = newManager;
		return ui;
	}
	
	public void addChild(Component component) {
		component.setParent(this);
	}
	public void addChild(int pos, Component component) {
		component.setParent(this, pos);
	}

	public Component attribute(String name, Object attr) {
		attributes.put(name, attr);
		if (name=="children") {
			Component[] children = (Component[]) attr;
			for (Component c: getChildren()) c.delete();
			for (Component child: children) addChild(child);
		}
		if (getUI()!=null) getUI().attribute(name, attr);
		invalidate();
		return this;
	}
	public void setParent(Component parent) {
		setParent(parent, parent.children.size());
	}
	public void setParent(Component parent, int pos) {
		if (this.parent!=null) this.parent.delete(this);
		this.parent = parent;
		parent.children.add(pos, this);
		if (getUI()!=null) signalUIChange();
		invalidate();
	}

	private void signalUIChange() {
		ComponentUI ui = getUI();
		attributes.forEach((n,o)->ui.attribute(n, o));
		for (Component child: getChildren()) child.signalUIChange();
	}

	public void delete() {
		if (parent!=null) parent.delete(this);
	}
	public void delete(Component component) {
		children.remove(component);
		component.parent = null;
		invalidate();
	}

	public Component attribute(String name, Runnable attr) {
		return attribute(name, (Object) attr);
	}
	
	public void calcSize(Renderer r, SizePosGroup sizepos, DrawData data) {
		getUI().calcSize(r, sizepos, data);
	}
	public void draw(Renderer r, DrawData data) {
		getUI().draw(r, data);
	}

	public Component[] getChildren() {
		return children.toArray(new Component[children.size()]);
	}
	
	public void invalidate() {
		if (parent!=null) parent.invalidate();
	}

	public MouseBinding boundBind(MouseBinding binding) {
		return getUI().boundBind(binding);
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
		return query(c->c.attributes.containsKey("id")&&c.attributes.get("id").equals(id))[0];
	}

	public Object getAttribute(String key) {
		return attributes.get(key);
	}
	
}