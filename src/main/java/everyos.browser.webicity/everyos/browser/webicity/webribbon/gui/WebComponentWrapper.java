package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.engine.ribbon.core.component.BlockComponent;

public class WebComponentWrapper extends BlockComponent {
	private WebComponent wui;

	public WebComponentWrapper() {
		super();
	}

	public WebComponentWrapper ui(WebComponent component) {
		this.wui = component;
		invalidate();
		return this;
	}
	public WebComponent getUI() {
		return this.wui;
	}
}
