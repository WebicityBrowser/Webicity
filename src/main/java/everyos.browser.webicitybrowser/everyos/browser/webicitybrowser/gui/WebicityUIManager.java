package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.*;
import everyos.browser.webicitybrowser.gui.ui.*;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(CircularText.class, (c, p)->new CircularTextUI(c, p));
		ui.put(WebicityButton.class, WebicityButtonUI::new);
		ui.put(OverlyingBlockComponent.class, OverlyingBlockComponentUI::new);
		ui.put(TabButton.class, TabButtonUI::new);
		ui.put(URLBar.class, (c, p)->new URLBarUI(c, p));
		ui.put(WebComponentWrapper.class, (c, p)->new WebComponentWrapperUI(c, p));
		
		return ui;
	}
}
