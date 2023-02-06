package everyos.browser.webicitybrowser.gui.binding.component.tab;

import everyos.browser.webicitybrowser.gui.colors.ColorPalette;
import everyos.browser.webicitybrowser.ui.Tab;

public interface TabSystemConfiguration {

	ColorPalette getColors();
	
	TabDisplayComponent createTabDisplay(Tab tab);
	
	Tab createTab();
	
}
