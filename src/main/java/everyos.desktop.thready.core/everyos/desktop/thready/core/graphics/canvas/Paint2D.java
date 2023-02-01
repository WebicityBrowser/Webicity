package everyos.desktop.thready.core.graphics.canvas;

import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public interface Paint2D {

	ColorFormat getColor();
	
	LoadedFont getLoadedFont();
	
}
