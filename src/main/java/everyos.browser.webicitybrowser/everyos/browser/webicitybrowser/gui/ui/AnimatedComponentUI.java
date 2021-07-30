package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Animation;
import everyos.browser.webicitybrowser.gui.component.AnimatedComponent;
import everyos.browser.webicitybrowser.gui.directive.AnimationDirective;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.appearence.DefaultAppearence;
import everyos.engine.ribbon.ui.simple.layout.InlineBlockLayout;
import everyos.engine.ribbon.ui.simple.layout.Layout;

public class AnimatedComponentUI extends SimpleBlockComponentUI {
	private AnimatedComponent component;
	private Layout layout;
	private Appearence appearence;

	public AnimatedComponentUI(Component c, ComponentUI parent) {
		super(c, parent);
		component = getComponent().casted(AnimatedComponent.class);
		this.layout = new AnimatedComponentLayout(c, parent);
		this.appearence = new DefaultAppearence();
	}

	@Override
	public Layout getLayout() {
		return layout;
	}
	
	@Override
	public Appearence getAppearence() {
		return appearence;
	}
	
	private class AnimatedComponentLayout extends InlineBlockLayout {
		private Animation animation;
		
		public AnimatedComponentLayout(Component component, ComponentUI ui) {
			super(component, ui);
		}

		@Override
		public void paint(Renderer r, Appearence appearence) {
			if (animation != null) {
				Rectangle bounds = getBounds();
				Renderer r3 = r.getSubcontext(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
				Renderer r2 = animation.step(r3, component.isVisible(), new Dimension(bounds.getWidth(), bounds.getHeight()));
				paintMouse(r2, appearence);
				paintInnerPart(r2, appearence);
			}
		}
		
		@Override
		public void directive(UIDirective directive) {
			if (directive instanceof AnimationDirective) {
				this.animation = ((AnimationDirective) directive).getAnimation();
			} else {
				super.directive(directive);
			}
		}
	}
}
