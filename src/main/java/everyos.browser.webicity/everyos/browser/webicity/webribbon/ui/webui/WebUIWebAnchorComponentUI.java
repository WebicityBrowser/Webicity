package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.GUIWebComponentUI;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;

public class WebUIWebAnchorComponentUI extends WebUIWebComponentUI {
	public WebUIWebAnchorComponentUI() {}
	@Override public WebComponentUI create(WebComponent component, WebComponentUI parent) {
		return new WebUIWebAnchorComponentUI(component, (GUIWebComponentUI) parent);
	}
	
	public WebUIWebAnchorComponentUI(WebComponent component, GUIWebComponentUI parent) {
		super(component, parent);
	}
	
	@Override public void render(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr) {
		renderUI(r, sizepos, uimgr); //TODO: Styling instead
	}
	
	@Override public void paintUI(GUIRenderer r) {
		r.setForeground(Color.BLUE);
		paintChildren(r);
	}
}
