package com.github.webicitybrowser.webicitybrowser.gui.colors;

import com.github.webicitybrowser.thready.color.format.ColorFormat;

public interface ColorPalette {

	ColorFormat getBackgroundPrimary();
	
	ColorFormat getBackgroundSecondary();
	
	ColorFormat getBackgroundSecondaryHover();
	
	ColorFormat getBackgroundSecondarySelected();
	
	ColorFormat getBackgroundSecondaryActive();
	
	ColorFormat getBackgroundSecondaryDanger();
	
	ColorFormat getForegroundPrimary();
	
}
