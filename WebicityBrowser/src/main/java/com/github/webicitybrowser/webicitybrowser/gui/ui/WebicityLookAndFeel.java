package com.github.webicitybrowser.webicitybrowser.gui.ui;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;
import com.github.webicitybrowser.webicitybrowser.gui.ui.button.CircularButtonComponentUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.frame.FrameUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.menu.MenuButtonComponentUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.tab.TabComponentUI;
import com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar.URLBarComponentUI;

public final class WebicityLookAndFeel {
	
	private WebicityLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder)  {
		lookAndFeelBuilder.registerComponentUI(CircularButtonComponent.class, CircularButtonComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(MenuButtonComponent.class, MenuButtonComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(TabComponent.class, TabComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(URLBarComponent.class, URLBarComponentUI::new);
		lookAndFeelBuilder.registerComponentUI(FrameComponent.class, FrameUI::new);
	}

}
