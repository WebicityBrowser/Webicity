package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.ui.webui.layout.BlockLayout;
import everyos.browser.webicity.webribbon.ui.webui.layout.Layout;

public class WebUIWebDivComponentUI extends WebUIWebComponentUI {
	private Layout layout;

	public WebUIWebDivComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
		this.layout = new BlockLayout(component, parent);
	}
	
	@Override
	public Layout getLayout() {
		return this.layout;
	}
}
