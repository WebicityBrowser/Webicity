package everyos.browser.webicity.webribbon;

import everyos.browser.webicity.webribbon.component.WebComponent;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;

public class WebComponentWrapper extends BlockComponent {
	private WebComponent wui;

	public WebComponentWrapper(Component parent) {
		super(parent);
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
