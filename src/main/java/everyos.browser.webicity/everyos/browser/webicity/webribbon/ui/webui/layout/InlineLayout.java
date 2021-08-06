package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.shape.Marker;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.helper.ComputedChildrenHelper;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class InlineLayout implements Layout {
	private WebComponent component;
	private WebComponentUI ui;
	
	private ComputedChildrenHelper computedChildrenHelper;
	private Rectangle bounds;
	
	public InlineLayout(WebComponent component, WebComponentUI ui) {
		this.component = component;
		this.ui = ui;
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
		Marker marker = new Marker(sizepos.getMarker());
		sizepos.setMarker(marker);
		renderInnerPart(rd, sizepos, context, appearence);
		sizepos.setMarker(marker.end());
		this.bounds = marker.getHitbox();
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context, Appearence appearence) {
		GUIState state = rd.getState();
		rd.restoreState(state.clone());
		
		paintInnerPart(rd, viewport, context, appearence);
		
		rd.restoreState(state);
	}
	
	@Override
	public void processEvent(UIEvent event) {
		
	}
	
	private void renderInnerPart(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
		appearence.render(rd, sizepos, context);
		renderChildren(rd, sizepos, context);
	}
	
	private void renderChildren(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		this.computedChildrenHelper.recompute(c->context.getManager().get(c, ui));
		
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			c.render(rd, sizepos, context);
		}
	}
	
	private void paintInnerPart(RendererData rd, Rectangle viewport, WebPaintContext context, Appearence appearence) {
		appearence.paint(rd, viewport, context);
		paintChildren(rd, viewport, context);
	}
	
	private void paintChildren(RendererData rd, Rectangle viewport, WebPaintContext context) {
		rd.useBackground();
		GUIState state = rd.getState();
		rd.restoreState(state.clone());
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			if (c.getUIBox().intersectsWith(viewport)) {
				c.paint(rd, viewport, context);
				rd.restoreState(state);
			}
		}
		//TODO: Sort by Z-index
	}

	@Override
	public UIBox getComputedUIBox() {
		if (bounds == null) {
			return viewport -> true;
		}
		return viewport->viewport.intersects(bounds);
	}
}
