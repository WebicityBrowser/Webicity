package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private WebComponent wui;
	private WebComponentUI ui;
	private WebUIManager wuim;
	private Rectangle viewport;
	
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
			Rectangle bounds = getBounds();
			everyos.browser.webicity.webribbon.gui.shape.SizePosGroup spg = new everyos.browser.webicity.webribbon.gui.shape.SizePosGroup(
				bounds.getWidth(), bounds.getHeight(), 0, 0);
			this.viewport = new Rectangle(0, 0, spg.getSize().getWidth(), spg.getSize().getHeight());
			ui.render(r, spg, new UIContextImp(wuim));
		}
	}
	
	int i=0;

	@Override
	protected void paintUI(Renderer r) {
		r.restoreState(new GUIState());
		if (wui!=null) {
			ui.paint(r, viewport);
		}
	}
	
	private class UIContextImp implements UIContext {
		private WebUIManager uimanager;

		public UIContextImp(WebUIManager uimgr) {
			this.uimanager = uimgr;
		}

		@Override
		public WebUIManager getManager() {
			return uimanager;
		}

		@Override
		public void addTopLevelUIBox(UIBox box) {
			// TODO: Implement this when needed
		}
	}
}
