package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.GUIWebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;

public class WebUIWebBreakComponentUI extends WebUIWebComponentUI {
	public WebUIWebBreakComponentUI() {}
	@Override public WebComponentUI create(WebComponent component, WebComponentUI parent) {
		return new WebUIWebBreakComponentUI(component, (GUIWebComponentUI) parent);
	}
	
	public WebUIWebBreakComponentUI(WebComponent component, GUIWebComponentUI parent) {
		super(component, parent);
	}
	
	public void renderUI(GUIRenderer r, SizePosGroup sizepos) {
		sizepos.nextLine();
	}
}
