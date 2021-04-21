package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebAnchorComponent;
import everyos.browser.webicity.webribbon.core.component.WebBreakComponent;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.component.WebScriptComponent;
import everyos.browser.webicity.webribbon.core.component.WebStyleComponent;
import everyos.browser.webicity.webribbon.core.component.WebTextComponent;
import everyos.browser.webicity.webribbon.core.component.WebTitleComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;

public class WebUIWebUIManager {
	private WebUIWebUIManager() {};
	
	public static WebUIManager createUI() {
		WebUIManager wuim = new WebUIManager();
		wuim.put(WebComponent.class, (c, p)->new WebUIWebComponentUI(c,p));
		wuim.put(WebTextComponent.class, (c, p)->new WebUIWebTextComponentUI(c,p));
		wuim.put(WebBreakComponent.class, (c, p)->new WebUIWebBreakComponentUI(c,p));
		wuim.put(WebAnchorComponent.class, (c, p)->new WebUIWebAnchorComponentUI(c,p));
		
		wuim.put(WebTitleComponent.class, (c, p)->new WebUIWebIgnoredComponentUI(c,p));
		wuim.put(WebScriptComponent.class, (c, p)->new WebUIWebIgnoredComponentUI(c,p));
		wuim.put(WebStyleComponent.class, (c, p)->new WebUIWebIgnoredComponentUI(c,p));
		
		return wuim;
	}
}
