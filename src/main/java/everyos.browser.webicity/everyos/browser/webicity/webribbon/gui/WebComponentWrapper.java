package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class WebComponentWrapper extends BlockComponent {
	private WebComponent wui;

	public WebComponentWrapper() {
		super();
	}

	public WebComponentWrapper ui(WebComponent component) {
		this.wui = component;
		invalidate(InvalidationLevel.RENDER);
		return this;
	}
	public WebComponent getUI() {
		return this.wui;
	}
}
