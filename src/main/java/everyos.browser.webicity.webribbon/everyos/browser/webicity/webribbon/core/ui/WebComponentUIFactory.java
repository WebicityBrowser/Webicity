package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;

public interface WebComponentUIFactory {
	public WebComponentUI create(WebComponent component, WebComponentUI parent);
}
