package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.browser.webicity.webribbon.ui.webui.helper.ComputedChildrenHelper;
import everyos.browser.webicity.webribbon.ui.webui.psuedo.ScrollBar;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.helper.RectangleBuilder;	

public class InlineBlockLayout implements Layout {
	private WebComponent component;
	private WebComponentUI ui;
	private Dimension outerSize;
	private Dimension pageSize;
	private ComputedChildrenHelper computedChildrenHelper;
	private Position position;
	private ScrollBar scrollBar;
	private boolean requiresMouseTarget = false;
	
	public InlineBlockLayout(WebComponent component, WebComponentUI ui) {
		this.component = component;
		this.ui = ui;
		this.scrollBar = new ScrollBar(ui);
		this.computedChildrenHelper = new ComputedChildrenHelper(this.component);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
		this.position = sizepos.getCurrentPointer();
		
		Dimension maxBlockSize = getMaxBlockSize(sizepos);
	
		SizePosGroup temporaryPageBounds = new SizePosGroup(
			sizepos.getSize().getWidth(), 0,
			0, 0,
			-1, -1);
		
		renderInnerPart(rd, temporaryPageBounds, context, appearence);
		
		this.outerSize = temporaryPageBounds.getSize();
		
		//TODO: Make SizePosGroup#compareTo a thing
		if (maxBlockSize.getHeight()<temporaryPageBounds.getSize().getHeight() && maxBlockSize.getHeight()!=-1) {
			if (maxBlockSize.getWidth()!=-1) {
				temporaryPageBounds = new SizePosGroup(
					sizepos.getSize().getWidth()-10, 0,
					0, 0,
					-1, -1);
				
				renderInnerPart(rd, temporaryPageBounds, context, appearence);
				
				this.outerSize = new Dimension(maxBlockSize.getWidth()-10, maxBlockSize.getHeight());
			}
			
			requiresMouseTarget = true;
		}
		
		this.pageSize = temporaryPageBounds.getSize();

		sizepos.move(pageSize.getWidth(), true);
		sizepos.setMinLineHeight(pageSize.getHeight());
		sizepos.moveY(pageSize.getHeight()-temporaryPageBounds.getMinLineHeight());
		
		scrollBar.render(position, outerSize, pageSize);
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context, Appearence appearence) {
		
		scrollBar.paint(rd, new Rectangle(position.getX(), position.getY(), outerSize.getWidth(), outerSize.getHeight()), context);
		
		if (requiresMouseTarget) {
			context.getRenderer().paintMouseListener(rd, component, position.getX(), position.getY(), outerSize.getWidth()+10, outerSize.getHeight(), e->{
				processEvent(e);
			});
		}
		
		GUIState state = rd.getState();
		rd.restoreState(state.clone());
		RendererData childR = rd.getSubcontext(position.getX(), position.getY(), outerSize.getWidth(), outerSize.getHeight());
		
		paintInnerPart(childR, viewport, context, appearence);
		
		rd.restoreState(state);
	}
	
	@Override
	public void processEvent(UIEvent event) {
		if (this.pageSize!=null) {
			scrollBar.processEvent(event);
		}
	}
	
	protected Dimension getMaxBlockSize(SizePosGroup sizepos) {
		return new Dimension(-1, -1);
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
		int curScrollY = scrollBar.getCurrentScrollY();
		
		rd.translate(0, -curScrollY);
		
		Rectangle vp = intersect(viewport, curScrollY);
		
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			rd.useBackground();
			if (c.getUIBox().intersectsWith(vp)) {
				c.paint(rd, vp, context);
			}
		}
		//TODO: Sort by Z-index
	}
	
	private Rectangle intersect(Rectangle viewport, int scroll) {
		//AABB based culling
		RectangleBuilder vpBuilder = new RectangleBuilder(
			position.getX(), position.getY(),
			outerSize.getWidth(), outerSize.getHeight());
		
		// Perform an intersect
		Rectangle intersected = vpBuilder.build().intersect(viewport);
		vpBuilder.setWidth(intersected.getWidth());
		vpBuilder.setHeight(intersected.getHeight());
				
		// Origin should be 0, 0
		vpBuilder.setX(intersected.getX()-position.getX());
		vpBuilder.setY(intersected.getY()-position.getY());
		
		// And scroll
		vpBuilder.setY(vpBuilder.getY()+scroll);
		
		return vpBuilder.build();
	}

	@Override
	public UIBox getComputedUIBox() {
		return viewport->viewport.intersects(new Rectangle(position.getX(), position.getY(), outerSize.getWidth(), outerSize.getHeight()));
	}
}
