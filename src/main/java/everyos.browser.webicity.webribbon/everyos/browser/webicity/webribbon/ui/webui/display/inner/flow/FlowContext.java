package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public interface FlowContext {
	
	void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context);
	
}
