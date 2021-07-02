package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.javadom.intf.Element;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebAnchorComponentUI extends WebUIWebComponentUI {
	private AnchorAppearence appearence;

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
			if (ev.getAction()==MouseEvent.PRESS && ev.getButton()==MouseEvent.LEFT_BUTTON) {
				//TODO: Resolve url properly
				String href = ((Element) getComponent().getNode()).getAttribute("href");
				getComponent().getRenderer().getFrame().browse(href);
			}
		}
		super.processEvent(event);
	}
	
	private class AnchorAppearence implements Appearence {
		@Override
		public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
			
		}

		@Override
		public void paint(Renderer r, Rectangle viewport) {
			r.setForeground(Color.BLUE);
		}
	}
}
