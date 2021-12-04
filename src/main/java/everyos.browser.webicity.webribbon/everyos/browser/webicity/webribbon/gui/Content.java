package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public interface Content {
	
	void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context);
	//void composite(RendererData rd);
	
	//TODO: Actual method header for more advanced behaviour
	Content split();
	
}
