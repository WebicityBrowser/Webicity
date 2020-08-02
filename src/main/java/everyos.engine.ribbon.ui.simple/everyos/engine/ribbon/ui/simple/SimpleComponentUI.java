package everyos.engine.ribbon.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public class SimpleComponentUI extends GUIComponentUI {
	private Component component;
	private ArrayList<ComponentUI> children;
	private GUIComponentUI parent;
	private boolean invalidated;
	
	public SimpleComponentUI() {}
	public SimpleComponentUI(Component component, GUIComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new SimpleComponentUI(c, (GUIComponentUI) parent);
	};
	
	@Override public void render(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		renderUI(r, sizepos, uimgr);
	}
	protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	protected void renderChildren(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		for (GUIComponentUI child: calcChildren(uimgr)) child.render(r, sizepos, uimgr);
	}
	
	@Override public void paint(GUIRenderer r) {
		paintUI(r);
	}
	protected void paintUI(GUIRenderer r) {
		paintChildren(r);
	}
	
	protected void paintChildren(GUIRenderer r) {
		for (GUIComponentUI child: getChildren()) child.paint(r);
	}
	
	
	protected GUIComponentUI[] calcChildren(UIManager<GUIComponentUI> uimgr) {
		this.children = new ArrayList<ComponentUI>();
		for (Component child: component.getChildren()) {
			GUIComponentUI ui = uimgr.get(child, parent);
			child.bind(ui);
			children.add(ui);
		}
		return children.toArray(new GUIComponentUI[children.size()]);
	}
	protected GUIComponentUI[] getChildren() {
		return children.toArray(new GUIComponentUI[children.size()]);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Component> T getComponent() {
		return (T) component;
	}
	
	@Override public void directive(UIDirective directive) {
		
	}
	
	@Override public GUIComponentUI getParent() {
		return parent;
	}
	
	@Override public void invalidate() {
		GUIComponentUI cui = this;
		while (cui!=null) {
			if (cui.getValidated()) return;
			cui.invalidateLocal();
			cui = cui.getParent();
		}
	}
	@Override public void invalidateLocal() {
		this.invalidated = true;
	}
	@Override public void validate() {
		this.invalidated = false;
	}
	@Override public boolean getValidated() {
		return this.invalidated;
	}
}
