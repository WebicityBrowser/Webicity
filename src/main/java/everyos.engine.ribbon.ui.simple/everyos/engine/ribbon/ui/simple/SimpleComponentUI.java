package everyos.engine.ribbon.ui.simple;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.ForegroundDirective;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.appearence.DefaultAppearence;
import everyos.engine.ribbon.ui.simple.layout.InlineLayout;
import everyos.engine.ribbon.ui.simple.layout.Layout;


public class SimpleComponentUI implements ComponentUI {
	private final Component component;
	private final ComponentUI parent;
	private final Layout layout;
	private final Appearence appearence;
	
	private InvalidationLevel invalidated = InvalidationLevel.IGNORE;
	private PaintFill background;
	private PaintFill foreground;
	
	public SimpleComponentUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
		
		this.layout = new InlineLayout(component, this);
		this.appearence = new DefaultAppearence();
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
		getLayout().render(rd, sizepos, context, getAppearence());
		validateTo(InvalidationLevel.PAINT);
	}
	
	@Override
	public void paint(RendererData rd, PaintContext context) {
		if (background != null) {
			rd.getState().setBackground(background);
		}
		if (foreground != null) {
			rd.getState().setForeground(foreground);
		}
		getLayout().paint(rd, context, getAppearence());
		validateTo(InvalidationLevel.IGNORE);
	}
	
	@Override
	public ComponentUI getParent() {
		return parent;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		ComponentUI parentUI = this;
		while (parentUI != null) {
			if (!parentUI.getValidated(level)) {
				return;
			}
			parentUI.invalidateLocal(level);
			parentUI = parentUI.getParent();
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
		if (parent != null) {
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
		if (directive instanceof BackgroundDirective) {
			background = ((BackgroundDirective) directive).getBackground();
		}
		if (directive instanceof ForegroundDirective) {
			foreground = ((ForegroundDirective) directive).getForeground();
		}
		
		getLayout().directive(directive);
		getAppearence().directive(directive);
	}
}
