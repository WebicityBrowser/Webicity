package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class WebUIWebBreakComponentUI extends WebUIWebComponentUI {
	private BreakAppearence appearence;

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
		public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
			sizepos.nextLine();
		}

		@Override
		public void paint(Renderer r, Rectangle viewport) {
			
		}
	}
}
