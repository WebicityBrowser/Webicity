package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.WebComponentWrapper;
import everyos.browser.webicity.webribbon.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.ui.CircularTextUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.engine.ribbon.graphics.ui.UIManager;
import everyos.engine.ribbon.graphics.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(URLBar.class, new URLBarUI());
		ui.put(CircularText.class, new CircularTextUI());
		ui.put(WebComponentWrapper.class, new WebComponentWrapperUI());
		
		return ui;
	}
}
