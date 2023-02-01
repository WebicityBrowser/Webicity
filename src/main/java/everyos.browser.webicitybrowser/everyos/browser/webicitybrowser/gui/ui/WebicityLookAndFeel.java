package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.browser.webicitybrowser.component.MenuButtonComponent;
import everyos.browser.webicitybrowser.gui.ui.button.CircularButtonComponentUI;
import everyos.browser.webicitybrowser.gui.ui.menu.MenuButtonComponentUI;
import everyos.desktop.thready.core.gui.laf.LookAndFeelBuilder;

public final class WebicityLookAndFeel {
	
	private WebicityLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder)  {
		lookAndFeelBuilder.registerComponentUI(CircularButtonComponent.class, CircularButtonComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(MenuButtonComponent.class, MenuButtonComponentUI::new);
	}

}
