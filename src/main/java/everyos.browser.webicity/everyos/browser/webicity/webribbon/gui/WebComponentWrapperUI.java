package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.Dimension;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private WebComponent wui;
	private GUIWebComponentUI ui;
	private WebUIManager<GUIWebComponentUI> wuim;
	
	public WebComponentWrapperUI() {}
	public WebComponentWrapperUI(Component c, GUIComponentUI parent) {
		super(c, parent);
		
		wuim = WebUIWebUIManager.createUI();
	}
	@Override public ComponentUI create(Component c, ComponentUI parent) {
		return new WebComponentWrapperUI(c, (GUIComponentUI) parent);
	};

	@Override protected void renderUI(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr) {
		WebComponent owui = this.wui;
		this.wui = this.<WebComponentWrapper>getComponent().getUI();
		if (wui!=owui) {
			this.ui = wuim.get(wui, null);
		}
		if (wui!=null) {
			ui.render(r, new everyos.browser.webicity.webribbon.gui.shape.SizePosGroup(
				new Position(),
				new Dimension(sizepos.size.width, sizepos.size.height),
				sizepos.size.width),
				wuim);
		}
	}

	@Override protected void paintUI(GUIRenderer r) {
		if (wui!=null) {
			ui.paint(r);
		}
	}
}
