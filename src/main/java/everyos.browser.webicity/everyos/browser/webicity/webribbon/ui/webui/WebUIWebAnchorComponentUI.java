package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class WebUIWebAnchorComponentUI extends WebUIWebComponentUI {
	private final AnchorAppearence appearence;

	public WebUIWebAnchorComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		
		this.appearence = new AnchorAppearence();
	}
	
	@Override
	public Appearence getAppearence() {
		return this.appearence;
	}
	
	@Override
	public void processEvent(UIEvent event) {
		if (event instanceof MouseEvent && !((MouseEvent) event).isExternal()) {
			MouseEvent ev = (MouseEvent) event;
			if (ev.getAction() == MouseEvent.PRESS && ev.getButton() == MouseEvent.LEFT_BUTTON) {
				//TODO: Resolve url properly
				String href = ((Element) getComponent().getNode()).getAttribute("href");
				getComponent().getRenderer().getFrame().browse(href);
			}
		}
		super.processEvent(event);
	}
	
	private class AnchorAppearence implements Appearence {
		@Override
		public void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence) {
			
		}
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
			
		}

		@Override
		public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
			rd.getState().setForeground(new Color(0x00, 0x00, 0xEE));
		}
	}
}
