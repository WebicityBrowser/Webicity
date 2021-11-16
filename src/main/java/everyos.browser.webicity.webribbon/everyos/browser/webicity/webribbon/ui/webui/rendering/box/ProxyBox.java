package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class ProxyBox extends CachedRenderBox {

	public ProxyBox(InlineLevelBox box) {
		super(box);
	}
	
	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		getBox().paint(rd, viewport, context);;
	}

}
