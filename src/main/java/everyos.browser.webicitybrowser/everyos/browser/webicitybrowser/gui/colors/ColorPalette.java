package everyos.browser.webicitybrowser.gui.colors;

import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;

public interface ColorPalette {

	ColorFormat getBackgroundPrimary();
	
	ColorFormat getBackgroundSecondary();
	
	ColorFormat getBackgroundSecondaryHover();
	
	ColorFormat getBackgroundSecondarySelected();
	
	ColorFormat getBackgroundSecondaryActive();
	
	ColorFormat getBackgroundSecondaryDanger();
	
	ColorFormat getForegroundPrimary();
	
}
