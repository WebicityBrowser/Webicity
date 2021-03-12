package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebAnchorComponent;
import everyos.browser.webicity.webribbon.core.component.WebBreakComponent;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.component.WebScriptComponent;
import everyos.browser.webicity.webribbon.core.component.WebStyleComponent;
import everyos.browser.webicity.webribbon.core.component.WebTextComponent;
import everyos.browser.webicity.webribbon.core.component.WebTitleComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.helper.WebReflectiveFactory;

public class WebUIWebUIManager {
	private WebUIWebUIManager() {};
	
	public static WebUIManager createUI() {
		WebUIManager wuim = new WebUIManager();
		wuim.put(WebComponent.class, new WebReflectiveFactory(WebUIWebComponentUI.class));
		wuim.put(WebTextComponent.class, new WebReflectiveFactory(WebUIWebTextComponentUI.class));
		wuim.put(WebBreakComponent.class, new WebReflectiveFactory(WebUIWebBreakComponentUI.class));
		wuim.put(WebAnchorComponent.class, new WebReflectiveFactory(WebUIWebAnchorComponentUI.class));
		
		wuim.put(WebTitleComponent.class, new WebReflectiveFactory(WebUIWebIgnoredComponentUI.class));
		wuim.put(WebScriptComponent.class, new WebReflectiveFactory(WebUIWebIgnoredComponentUI.class));
		wuim.put(WebStyleComponent.class, new WebReflectiveFactory(WebUIWebIgnoredComponentUI.class));
		
		return wuim;
	}
}
