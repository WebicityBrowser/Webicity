package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.*;
import everyos.browser.webicitybrowser.gui.ui.*;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager createUI() {
		UIManager ui = SimpleUIManager.createUI();
		ui.put(TextButton.class, TextButtonUI::new);
		ui.put(CircularText.class, TextButtonUI::new);
		ui.put(WebicityButton.class, TextButtonUI::new);
		ui.put(OverlyingBlockComponent.class, OverlyingBlockComponentUI::new);
		ui.put(TabButton.class, TabButtonUI::new);
		ui.put(URLBar.class, URLBarUI::new);
		ui.put(WebComponentWrapper.class, WebComponentWrapperUI::new);
		
		return ui;
	}
}
