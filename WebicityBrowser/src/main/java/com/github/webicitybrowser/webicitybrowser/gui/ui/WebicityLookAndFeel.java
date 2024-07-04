package com.github.webicitybrowser.webicitybrowser.gui.ui;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.MenuButtonComponent;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;
import com.github.webicitybrowser.webicitybrowser.gui.ui.button.CircularButtonDisplay;
import com.github.webicitybrowser.webicitybrowser.gui.ui.frame.FrameDisplay;
import com.github.webicitybrowser.webicitybrowser.gui.ui.menu.MenuButtonDisplay;
import com.github.webicitybrowser.webicitybrowser.gui.ui.tab.TabDisplay;
import com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar.URLBarDisplay;

public final class WebicityLookAndFeel {
	
	private WebicityLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder)  {
		lookAndFeelBuilder.registerComponentUI(CircularButtonComponent.class, CircularButtonDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(MenuButtonComponent.class, MenuButtonDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(TabComponent.class, TabDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(URLBarComponent.class, URLBarDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(FrameComponent.class, FrameDisplay::componentUI);
	}

}
