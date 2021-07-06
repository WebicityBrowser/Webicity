package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.OverlyingBlockComponent;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.component.WebicityButton;
import everyos.browser.webicitybrowser.gui.ui.CircularTextUI;
import everyos.browser.webicitybrowser.gui.ui.OverlyingBlockComponentUI;
import everyos.browser.webicitybrowser.gui.ui.TabButtonUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.browser.webicitybrowser.gui.ui.WebicityButtonUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(CircularText.class, CircularTextUI::new);
		ui.put(WebicityButton.class, WebicityButtonUI::new);
		ui.put(OverlyingBlockComponent.class, OverlyingBlockComponentUI::new);
		ui.put(TabButton.class, TabButtonUI::new);
		ui.put(URLBar.class, URLBarUI::new);
		ui.put(WebComponentWrapper.class, WebComponentWrapperUI::new);
		
		return ui;
	}
}
