package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.browser.webicitybrowser.component.MenuButtonComponent;
import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabComponent;
import everyos.browser.webicitybrowser.gui.ui.button.CircularButtonComponentUI;
import everyos.browser.webicitybrowser.gui.ui.menu.MenuButtonComponentUI;
import everyos.browser.webicitybrowser.gui.ui.tab.TabComponentUI;
import everyos.browser.webicitybrowser.gui.ui.urlbar.URLBarComponentUI;
import everyos.desktop.thready.core.gui.laf.LookAndFeelBuilder;

public final class WebicityLookAndFeel {
	
	private WebicityLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder)  {
		lookAndFeelBuilder.registerComponentUI(CircularButtonComponent.class, CircularButtonComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(MenuButtonComponent.class, MenuButtonComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(TabComponent.class, TabComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(URLBarComponent.class, URLBarComponentUI::new);
	}

}
