package everyos.browser.webicity.webribbon.ui.webui.display.inner.none;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class EmptyContent implements Content {
	
	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		
	}

	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		
	}

	@Override
	public Content split() {
		return new EmptyContent();
	}

}
