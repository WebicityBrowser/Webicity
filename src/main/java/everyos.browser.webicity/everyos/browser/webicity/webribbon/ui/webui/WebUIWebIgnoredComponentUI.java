package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebIgnoredComponentUI extends WebUIWebComponentUI {
	public WebUIWebIgnoredComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {}
	
	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {}
	
	@Override
	public UIBox getUIBox() {
		return viewport->false;
	}
}
