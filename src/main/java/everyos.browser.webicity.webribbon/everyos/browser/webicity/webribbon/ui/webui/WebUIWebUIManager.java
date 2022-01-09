package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.component.WebDocumentComponent;
import everyos.browser.webicity.webribbon.core.component.WebScriptComponent;
import everyos.browser.webicity.webribbon.core.component.WebStyleComponent;
import everyos.browser.webicity.webribbon.core.component.WebTextComponent;
import everyos.browser.webicity.webribbon.core.component.WebTitleComponent;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.ui.webui.ui.WebUIWebComponentUI;
import everyos.browser.webicity.webribbon.ui.webui.ui.WebUIWebIgnoredComponentUI;
import everyos.browser.webicity.webribbon.ui.webui.ui.WebUIWebWindowUI;
import everyos.browser.webicity.webribbon.ui.webui.ui.content.text.WebUIWebTextComponentUI;

public class WebUIWebUIManager {

	public static WebUIManager createUI() {
		WebUIManager uiManager = new WebUIManager();
		
		uiManager.put(WebComponent.class, WebUIWebComponentUI::new);
		uiManager.put(WebDocumentComponent.class, WebUIWebWindowUI::new);
		uiManager.put(WebTextComponent.class, WebUIWebTextComponentUI::new);
		
		uiManager.put(WebScriptComponent.class, WebUIWebIgnoredComponentUI::new);
		uiManager.put(WebTitleComponent.class, WebUIWebIgnoredComponentUI::new);
		uiManager.put(WebStyleComponent.class, WebUIWebIgnoredComponentUI::new);
		
		return uiManager;
	}

}
