package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.ui.CircularTextUI;
import everyos.browser.webicitybrowser.gui.ui.TabButtonUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(CircularText.class, (c, p)->new CircularTextUI(c, p));
		ui.put(TabButton.class, (c, p)->new TabButtonUI(c, p));
		ui.put(URLBar.class, (c, p)->new URLBarUI(c, p));
		ui.put(WebComponentWrapper.class, (c, p)->new WebComponentWrapperUI(c, p));
		
		return ui;
	}
}
