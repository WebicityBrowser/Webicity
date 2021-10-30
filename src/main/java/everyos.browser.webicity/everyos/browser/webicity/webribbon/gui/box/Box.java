package everyos.browser.webicity.webribbon.gui.box;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface Box {
	
	Box[] getChildren();
	
	void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(RendererData rd, Rectangle viewport, WebPaintContext context);
	
	CullingFilter getPaintCullingFilter();
	
	// TODO: Should these exist?
	Position getFinalPos();
	void setFinalPos(Position currentPointer);
	
	Dimension getFinalSize();
	void setFinalSize(Dimension size);
	
}
