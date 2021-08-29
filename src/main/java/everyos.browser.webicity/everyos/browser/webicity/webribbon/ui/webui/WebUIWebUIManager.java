package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebAnchorComponent;
import everyos.browser.webicity.webribbon.core.component.WebBreakComponent;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.component.WebDivComponent;
import everyos.browser.webicity.webribbon.core.component.WebScriptComponent;
import everyos.browser.webicity.webribbon.core.component.WebStyleComponent;
import everyos.browser.webicity.webribbon.core.component.WebTextComponent;
import everyos.browser.webicity.webribbon.core.component.WebTitleComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;

public final class WebUIWebUIManager {
	private WebUIWebUIManager() {};
	
	public static WebUIManager createUI() {
		WebUIManager wuim = new WebUIManager();
		
		wuim.put(WebComponent.class, WebUIWebComponentUI::new);
		wuim.put(WebTextComponent.class, WebUIWebTextComponentUI::new);
		wuim.put(WebBreakComponent.class, WebUIWebBreakComponentUI::new);
		wuim.put(WebAnchorComponent.class, WebUIWebAnchorComponentUI::new);
		wuim.put(WebDivComponent.class, WebUIWebDivComponentUI::new);
		
		wuim.put(WebTitleComponent.class, WebUIWebIgnoredComponentUI::new);
		wuim.put(WebScriptComponent.class, WebUIWebIgnoredComponentUI::new);
		wuim.put(WebStyleComponent.class, WebUIWebIgnoredComponentUI::new);
		
		return wuim;
	}
}
