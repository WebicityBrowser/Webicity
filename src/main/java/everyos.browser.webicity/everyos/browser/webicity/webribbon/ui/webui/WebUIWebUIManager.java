package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebAnchorComponent;
import everyos.browser.webicity.webribbon.core.component.WebBreakComponent;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.component.WebTextComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.GUIWebComponentUI;

public class WebUIWebUIManager {
	private WebUIWebUIManager() {};
	
	public static WebUIManager<GUIWebComponentUI> createUI() {
		WebUIManager<GUIWebComponentUI> wuim = new WebUIManager<>();
		wuim.put(WebComponent.class, new WebUIWebComponentUI());
		wuim.put(WebTextComponent.class, new WebUIWebTextComponentUI());
		wuim.put(WebBreakComponent.class, new WebUIWebBreakComponentUI());
		wuim.put(WebAnchorComponent.class, new WebUIWebAnchorComponentUI());
		
		return wuim;
	}
}
