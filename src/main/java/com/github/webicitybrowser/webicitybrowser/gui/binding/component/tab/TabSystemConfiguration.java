package com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab;

import com.github.webicitybrowser.webicitybrowser.gui.colors.ColorPalette;
import com.github.webicitybrowser.webicitybrowser.ui.Tab;

public interface TabSystemConfiguration {

	ColorPalette getColors();
	
	TabDisplayComponent createTabDisplay(Tab tab);
	
	Tab createTab();
	
}
