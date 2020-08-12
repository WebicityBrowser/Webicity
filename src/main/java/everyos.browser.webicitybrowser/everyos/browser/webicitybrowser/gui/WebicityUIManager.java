package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapperUI;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.ui.CircularTextUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.GUIComponentUI;
import everyos.engine.ribbon.ui.simple.SimpleUIManager;

public class WebicityUIManager {
	public static UIManager<GUIComponentUI> createUI() {
		UIManager<GUIComponentUI> ui = SimpleUIManager.createUI();
		ui.put(URLBar.class, new URLBarUI());
		ui.put(CircularText.class, new CircularTextUI());
		ui.put(WebComponentWrapper.class, new WebComponentWrapperUI());
		
		return ui;
	}
}
