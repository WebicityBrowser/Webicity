package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;

public interface WebComponentUI {
	public WebComponentUI create(WebComponent c, WebComponentUI parent);
	public void invalidate();
}
