package everyos.engine.ribbon.ui.simple.layout;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.ComputedChildrenHelper;

public class InlineLayout implements Layout {
	private Component component;
	private ComponentUI ui;
	private ComputedChildrenHelper computedChildrenHelper;

	public InlineLayout(Component component, ComponentUI ui) {
		this.component = component;
		this.ui = ui;
	}

	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr, Appearence appearence) {
		appearence.render(r, sizepos, uimgr);
		renderChildren(r, sizepos, uimgr);

	}

	@Override
	public void paint(Renderer r, Appearence appearence) {
		appearence.paint(r);
		paintChildren(r);
	}

	@Override
	public void processEvent(UIEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void directive(UIDirective directive) {
		// TODO Auto-generated method stub

	}

	private void renderChildren(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component, c->uimgr.get(c, ui));

		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			c.render(r, sizepos, uimgr);
		}
	}

	private void paintChildren(Renderer r) {
		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			r.useBackground();
			c.paint(r);
		}
	}
}
