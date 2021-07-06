package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebWindowUI;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private Appearence appearence;
	
	public WebComponentWrapperUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new WebComponentWrapperAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
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
	
	private class WebComponentWrapperAppearence implements Appearence {
		private WebComponent webComponent;
		private WebUIWebWindowUI documentUI;
		private WebUIManager webUIManager;
		private Rectangle viewport;
		
		public WebComponentWrapperAppearence() {
			webUIManager = WebUIWebUIManager.createUI();
		}
		
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
			WebComponent oldWebComponent = this.webComponent;
			this.webComponent = getComponent().casted(WebComponentWrapper.class).getUI();
			if (webComponent!=oldWebComponent) {
				this.documentUI = new WebUIWebWindowUI(webComponent, null);
			}
			if (webComponent!=null) {
				Dimension bounds = sizepos.getSize();
				everyos.browser.webicity.webribbon.gui.shape.SizePosGroup spg = new everyos.browser.webicity.webribbon.gui.shape.SizePosGroup(
					bounds.getWidth(), bounds.getHeight(), 0, 0);
				this.viewport = new Rectangle(0, 0, spg.getSize().getWidth(), spg.getSize().getHeight());
				
				documentUI.setWindowSize(new Dimension(bounds.getWidth(), bounds.getHeight()));
				
				documentUI.render(r, spg, new UIContextImp(webUIManager));
			}
		}

		@Override
		public void paint(Renderer r) {
			r.restoreState(new GUIState());
			if (webComponent!=null) {
				documentUI.paint(r, viewport);
			}
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
	}
}
