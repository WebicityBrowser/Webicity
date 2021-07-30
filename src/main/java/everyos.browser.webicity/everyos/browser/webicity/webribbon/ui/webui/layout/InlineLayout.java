package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.Marker;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.helper.ComputedChildrenHelper;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class InlineLayout implements Layout {
	private WebComponent component;
	private WebComponentUI ui;
	
	private ComputedChildrenHelper computedChildrenHelper;
	private Rectangle bounds;
	
	public InlineLayout(WebComponent component, WebComponentUI ui) {
		this.component = component;
		this.ui = ui;
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context, Appearence appearence) {
		Marker marker = new Marker(sizepos.getMarker());
		sizepos.setMarker(marker);
		renderInnerPart(r, sizepos, context, appearence);
		sizepos.setMarker(marker.end());
		this.bounds = marker.getHitbox();
	}

	@Override
	public void paint(Renderer r, Rectangle viewport, Appearence appearence) {
		GUIState state = r.getState();
		r.restoreState(state.clone());
		
		paintInnerPart(r, viewport, appearence);
		
		r.restoreState(state);
	}
	
	@Override
	public void processEvent(UIEvent event) {
		
	}
	
	private void renderInnerPart(Renderer r, SizePosGroup sizepos, UIContext context, Appearence appearence) {
		appearence.render(r, sizepos, context);
		renderChildren(r, sizepos, context);
	}
	
	private void renderChildren(Renderer r, SizePosGroup sizepos, UIContext context) {
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component, c->context.getManager().get(c, ui));
		
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			c.render(r, sizepos, context);
		}
	}
	
	private void paintInnerPart(Renderer r, Rectangle viewport, Appearence appearence) {
		appearence.paint(r, viewport);
		paintChildren(r, viewport);
	}
	
	private void paintChildren(Renderer r, Rectangle viewport) {
		r.useBackground();
		GUIState state = r.getState();
		r.restoreState(state.clone());
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			if (c.getUIBox().intersectsWith(viewport)) {
				c.paint(r, viewport);
				r.restoreState(state);
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
