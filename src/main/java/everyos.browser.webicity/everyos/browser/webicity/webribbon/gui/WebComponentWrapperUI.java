package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebWindowUI;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class WebComponentWrapperUI extends SimpleBlockComponentUI {
	private final Appearence appearence;
	
	public WebComponentWrapperUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new WebComponentWrapperAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class WebRenderContextImp implements WebRenderContext {
		private final WebUIManager uimanager;

		public WebRenderContextImp(WebUIManager uimgr) {
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
	
	private class WebPaintContextImp implements WebPaintContext {
		private final Renderer renderer;

		public WebPaintContextImp(Renderer r) {
			this.renderer = r;
		}
		
		@Override
		public Pallete getPallete() {
			return getComponent().casted(WebComponentWrapper.class).getPallete();
		}

		@Override
		public Renderer getRenderer() {
			return renderer;
		}
	}
	
	private class WebComponentWrapperAppearence implements Appearence {
		private final WebUIManager webUIManager;
		
		private WebComponent webComponent;
		private WebUIWebWindowUI documentUI;
		private Rectangle viewport;
		
		public WebComponentWrapperAppearence() {
			this.webUIManager = WebUIWebUIManager.createUI();
		}
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			WebComponent oldWebComponent = this.webComponent;
			this.webComponent = getComponent().casted(WebComponentWrapper.class).getUI();
			if (webComponent != oldWebComponent) {
				this.documentUI = new WebUIWebWindowUI(webComponent, null);
				//documentUI.onInvalidation((level) -> invalidate(level));
			}
			if (webComponent != null) {
				Dimension bounds = sizepos.getSize();
				SizePosGroup spg = new SizePosGroup(bounds.getWidth(), bounds.getHeight(), 0, 0);
				this.viewport = new Rectangle(0, 0, spg.getSize().getWidth(), spg.getSize().getHeight());
				
				documentUI.setWindowSize(new Dimension(bounds.getWidth(), bounds.getHeight()));
				
				documentUI.render(rd, spg, new WebRenderContextImp(webUIManager));
			}
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			GUIState state = new GUIState();
			state.setFont(context.getRenderer().getFont("Times New Roman", 100, 12));
			rd.restoreState(state);
			
			if (webComponent != null) {
				documentUI.paint(rd, viewport, new WebPaintContextImp(context.getRenderer()));
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
