package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;

public class WebUIWebBreakComponentUI extends WebUIWebComponentUI {
	public WebUIWebBreakComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	public void renderUI(Renderer r, SizePosGroup sizepos) {
		sizepos.nextLine();
	}
}
