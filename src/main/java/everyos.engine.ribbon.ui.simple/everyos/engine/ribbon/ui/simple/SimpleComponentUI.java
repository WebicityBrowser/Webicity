package everyos.engine.ribbon.ui.simple;

import java.util.ArrayList;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ForegroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIConstants;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;


public class SimpleComponentUI implements ComponentUI {
	private Component component;
	private ArrayList<ComponentUI> children;
	private ComponentUI parent;
	private boolean invalidated = true;
	protected Color background; //TODO: Getter instead
	protected Color foreground;
	
	public SimpleComponentUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		renderUI(r, sizepos, uimgr);
	}
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		renderChildren(r, sizepos, uimgr);
	}
	protected void renderChildren(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		for (ComponentUI child: calcChildren(uimgr)) {
			GUIState state = r.getState().clone();
			child.render(r, sizepos, uimgr);
			child.validate();
			r.restoreState(state);
		}
	}
	
	@Override
	public void paint(Renderer r) {
		GUIState state = r.getState();
		if (background!=null) r.setBackground(background);
		if (foreground!=null) r.setForeground(foreground);
		paintUI(r);
		r.restoreState(state);
	}
	protected void paintUI(Renderer r) {
		paintChildren(r);
	}
	
	protected void paintChildren(Renderer r) {
		GUIState state = r.getState();
		for (ComponentUI child: getChildren()) {
			child.paint(r);
			r.restoreState(state);
		}
	}
	
	
	protected ComponentUI[] calcChildren(UIManager uimgr) {
		this.children = new ArrayList<ComponentUI>(1);
		for (Component child: component.getChildren()) {
			ComponentUI ui = uimgr.get(child, parent);
			child.bind(ui);
			children.add(ui);
		}
		return children.toArray(new ComponentUI[children.size()]);
	}
	protected ComponentUI[] getChildren() {
		return children.toArray(new ComponentUI[children.size()]);
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends Component> T getComponent() {
		return (T) component;
	}
	
	@Override
	public void directive(UIDirective directive) {
		if (directive instanceof BackgroundDirective) background = ((BackgroundDirective) directive).getBackground();
		if (directive instanceof ForegroundDirective) foreground = ((ForegroundDirective) directive).getForeground();
	}
	
	@Override
	public ComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void invalidate() {
		ComponentUI cui = this;
		while (cui!=null) {
			if (!cui.getValidated()) return;
			cui.invalidateLocal();
			cui = cui.getParent();
		}
	}
	
	@Override
	public void invalidateLocal() {
		this.invalidated = true;
	}
	@Override
	public void validate() {
		this.invalidated = false;
	}
	
	@Override
	public boolean getValidated() {
		return !this.invalidated;
	}
	
	protected void repaint() {
		ComponentUI cui = this;
		while (cui!=null) {
			cui.repaintLocal();
			cui = cui.getParent();
		}
	}
	@Override
	public void repaintLocal() {}
	
	@Override
	public void hint(int hint) {
		if (hint==GUIConstants.RENDER) invalidate();
		if (hint==GUIConstants.PAINT) repaint();
	}
}
