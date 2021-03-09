package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.ui.CircularTextUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;
import everyos.engine.ribbon.ui.simple.helper.ReflectiveFactory;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(CircularText.class, new ReflectiveFactory(CircularTextUI.class));
		ui.put(URLBar.class, new ReflectiveFactory(URLBarUI.class));
		
		return ui;
	}
}
