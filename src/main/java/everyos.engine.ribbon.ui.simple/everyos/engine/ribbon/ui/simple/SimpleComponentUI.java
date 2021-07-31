package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.ForegroundDirective;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.appearence.DefaultAppearence;
import everyos.engine.ribbon.ui.simple.layout.InlineLayout;
import everyos.engine.ribbon.ui.simple.layout.Layout;


public class SimpleComponentUI implements ComponentUI {
	private Layout layout;
	private Appearence appearence;
	private Component component;
	private ComponentUI parent;
	
	private InvalidationLevel invalidated = InvalidationLevel.IGNORE;
	private Color background;
	private Color foreground;
	
	public SimpleComponentUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
		
		this.layout = new InlineLayout(component, this);
		this.appearence = new DefaultAppearence();
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		getLayout().render(r, sizepos, uimgr, getAppearence());
		validateTo(InvalidationLevel.PAINT);
	}
	
	@Override
	public void paint(Renderer r) {
		if (background!=null) r.setBackground(background);
		if (foreground!=null) r.setForeground(foreground);
		getLayout().paint(r, getAppearence());
		validateTo(InvalidationLevel.IGNORE);
	}
	
	@Override
	public ComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		ComponentUI cui = this;
		while (cui!=null) {
			if (!cui.getValidated(level)) return;
			cui.invalidateLocal(level);
			cui = cui.getParent();
		}
	}
	
	@Override
	public void invalidateLocal(InvalidationLevel level) {
		if (this.invalidated.lessThan(level)) {
			this.invalidated = level;
		}
	}
	
	@Override
	public void validateTo(InvalidationLevel level) {
		this.invalidated = level;
	}
	
	@Override
	public boolean getValidated(InvalidationLevel reference) {
		return this.invalidated.lessThan(reference);
	}
	
	@Override
	public void processEvent(UIEvent event) {	
		layout.processEvent(event);
		if (parent!=null) {
			parent.processEvent(event);
		}
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}
	
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	protected Layout getLayout() {
		return this.layout;
	}

	@Override
	public void directive(UIDirective directive) {
		if (directive instanceof BackgroundDirective) background = ((BackgroundDirective) directive).getBackground();
		if (directive instanceof ForegroundDirective) foreground = ((ForegroundDirective) directive).getForeground();
		
		getLayout().directive(directive);
		getAppearence().directive(directive);
	}
}
