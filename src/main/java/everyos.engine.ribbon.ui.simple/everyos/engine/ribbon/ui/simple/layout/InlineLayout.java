package everyos.engine.ribbon.ui.simple.layout;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.ComputedChildrenHelper;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class InlineLayout implements Layout {
	private final Component component;
	private final ComponentUI ui;
	private final ComputedChildrenHelper computedChildrenHelper;

	public InlineLayout(Component component, ComponentUI ui) {
		this.component = component;
		this.ui = ui;
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component);
	}

	@Override
	public void render(RendererData rd, SizePosGroup sizepos, RenderContext context, Appearence appearence) {
		appearence.render(rd, sizepos, context);
		renderChildren(rd, sizepos, context);

	}

	@Override
	public void paint(RendererData rd, PaintContext context, Appearence appearence) {
		appearence.paint(rd, context);
		paintChildren(rd, context);
	}

	@Override
	public void processEvent(UIEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void directive(UIDirective directive) {
		// TODO Auto-generated method stub

	}

	private void renderChildren(RendererData rd, SizePosGroup sizepos, RenderContext context) {
		this.computedChildrenHelper.recompute(c->context.getUIManager().get(c, ui));
		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			c.render(rd, sizepos, context);
		}
	}

	private void paintChildren(RendererData rd, PaintContext context) {
		for (ComponentUI c: computedChildrenHelper.getChildren()) {
			rd.useBackground();
			c.paint(rd, context);
		}
	}
}
