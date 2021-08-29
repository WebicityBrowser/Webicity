package everyos.browser.webicity.webribbon.ui.webui.appearence;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface Appearence {
	void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(RendererData rd, Rectangle viewport, WebPaintContext context);
}
