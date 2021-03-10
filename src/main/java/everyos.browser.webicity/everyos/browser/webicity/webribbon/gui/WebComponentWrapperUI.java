package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private WebComponent wui;
	private WebComponentUI ui;
	private WebUIManager wuim;
	
	public WebComponentWrapperUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		wuim = WebUIWebUIManager.createUI();
	}

	@Override
	protected void renderUI(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		WebComponent owui = this.wui;
		this.wui = this.<WebComponentWrapper>getComponent().getUI();
		if (wui!=owui) {
			this.ui = wuim.get(wui, null);
		}
		if (wui!=null) {
			everyos.browser.webicity.webribbon.gui.shape.SizePosGroup spg = new everyos.browser.webicity.webribbon.gui.shape.SizePosGroup(
				new Position(),
				new Dimension(sizepos.size.width, sizepos.size.height),
				sizepos.size.width);
			ui.render(r, spg, wuim);
		}
	}

	@Override
	protected void paintUI(Renderer r) {
		r.restoreState(new GUIState());
		if (wui!=null) {
			ui.paint(r);
		}
	}
}
