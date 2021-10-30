package everyos.browser.webicity.webribbon.gui;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.spec.jcss.cssom.CSSOMUtil;
import everyos.browser.spec.jcss.intf.CSSRule;
import everyos.browser.spec.jcss.intf.CSSStyleSheet;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.core.ui.WebWindowUI;
import everyos.browser.webicity.webribbon.gui.box.MutableBlockLevelBox;
import everyos.browser.webicity.webribbon.ui.webui.WebUIWebUIManager;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
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
	
	private class WebBoxContextImp implements WebBoxContext {
		private final WebUIManager uimanager;

		public WebBoxContextImp(WebUIManager uimgr) {
			this.uimanager = uimgr;
		}

		@Override
		public WebUIManager getManager() {
			return uimanager;
		}
	}
	
	private class WebRenderContextImp implements WebRenderContext {
		
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
		private WebWindowUI documentUI;
		private Rectangle viewport;
		private MutableBlockLevelBox base;
		private CSSOMNode cssomRoot;
		
		public WebComponentWrapperAppearence() {
			this.webUIManager = WebUIWebUIManager.createUI();
		}
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			WebComponent oldWebComponent = this.webComponent;
			this.webComponent = getComponent().casted(WebComponentWrapper.class).getUI();
			if (webComponent != oldWebComponent) {
				this.documentUI = (WebWindowUI) webUIManager.get(webComponent, null);
				documentUI.onInvalidation((level) -> invalidate(level));
				
				//TODO: cssomRenderRecalculateRequired variable
				updateCSSOM(); //TODO?
				documentUI.recalculateCSSOM(cssomRoot, webUIManager);
			}
			if (webComponent != null) {
				Dimension bounds = sizepos.getSize();
				SizePosGroup spg = new SizePosGroup(bounds.getWidth(), bounds.getHeight(), 0, 0);
				this.viewport = new Rectangle(0, 0, spg.getSize().getWidth(), spg.getSize().getHeight());
				
				base = documentUI.createBox();
				base.setFinalPos(new Position(0, 0));
				base.setFinalSize(new Dimension(spg.getSize().getWidth(), spg.getSize().getHeight()));
				documentUI.box(base, new WebBoxContextImp(webUIManager));
				
				base.render(rd, spg, new WebRenderContextImp());
				
				// This works on my JVM, and I plan to distribute the JVM with the application
				// For some reason the garbage collector doesn't seem to automatically run
				// with a large or unbounded max heap, but we create a lot of garbage during processing
				System.gc();
			}
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			GUIState state = new GUIState();
			state.setFont(context.getRenderer().getFont("Times New Roman", 100, 12));
			rd.restoreState(state);
			
			if (webComponent != null) {
				base.paint(rd, viewport, new WebPaintContextImp(context.getRenderer()));
			}
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			
		}
		
		private void updateCSSOM() {
			List<CSSRule[]> appliedRules = new ArrayList<>();
			
			appliedRules.add(DefaultStyles.getCSSRules());
			
			{
				List<CSSStyleSheet> styleSheets = ((Document) documentUI.getComponent().getNode()).getDocumentOrShadowRootCSSStyleSheets();
				for (CSSStyleSheet styleSheet: styleSheets) {
					if (styleSheet.getDisabled()) {
						continue;
					}
					
					appliedRules.add(styleSheet.getCSSRules());
				}
			}
			
			this.cssomRoot = CSSOMUtil.computeCSSOM(appliedRules);
		}
	}
}
