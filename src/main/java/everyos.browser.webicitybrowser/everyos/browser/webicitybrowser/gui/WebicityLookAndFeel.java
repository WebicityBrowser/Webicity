package everyos.browser.webicitybrowser.gui;

import com.github.webicity.lace.core.laf.LookAndFeel;
import com.github.webicity.lace.laf.simple.SimpleLookAndFeel;

import everyos.browser.webicitybrowser.gui.component.CircularButton;
import everyos.browser.webicitybrowser.gui.component.TabButton;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.gui.component.WebicityMenuButton;
import everyos.browser.webicitybrowser.gui.ui.CircularButtonUI;
import everyos.browser.webicitybrowser.gui.ui.TabButtonUI;
import everyos.browser.webicitybrowser.gui.ui.URLBarUI;
import everyos.browser.webicitybrowser.gui.ui.WebicityMenuButtonUI;

public final class WebicityLookAndFeel {

	private WebicityLookAndFeel() {}

	public static LookAndFeel createLookAndFeel() {
		LookAndFeel lookAndFeel = SimpleLookAndFeel.createLookAndFeel();
		
		lookAndFeel.put(WebicityMenuButton.class, WebicityMenuButtonUI::new);
		lookAndFeel.put(CircularButton.class, CircularButtonUI::new);
		lookAndFeel.put(TabButton.class, TabButtonUI::new);
		lookAndFeel.put(URLBar.class, URLBarUI::new);
		
		return lookAndFeel;
	}
	
}
