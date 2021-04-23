package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebIgnoredComponentUI extends WebUIWebComponentUI {
	public WebUIWebIgnoredComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		setUIBox(viewport->false);
	}
	
	@Override
	public void paint(Renderer r, Rectangle viewport) {}
}
