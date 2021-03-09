package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;

public class WebUIWebAnchorComponentUI extends WebUIWebComponentUI {
	public WebUIWebAnchorComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, WebUIManager uimgr) {
		renderUI(r, sizepos, uimgr); //TODO: Styling instead
	}
	
	@Override
	public void paintUI(Renderer r) {
		r.setForeground(Color.BLUE);
		paintChildren(r);
	}
}
