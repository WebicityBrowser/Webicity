package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class WebUIWebBreakComponentUI extends WebUIWebComponentUI {
	private final BreakAppearence appearence;

	public WebUIWebBreakComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		this.appearence = new BreakAppearence();
	}
	
	@Override
	public Appearence getAppearence() {
		return this.appearence;
	}
	
	private class BreakAppearence implements Appearence {
		@Override
		public void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence) {
			
		}
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
			sizepos.nextLine();
		}

		@Override
		public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
			
		}
	}
	
}
