package everyos.desktop.thready.core.graphics.canvas;

import everyos.desktop.thready.core.graphics.color.RawColor;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public interface Paint2D {

	RawColor getColor();
	
	LoadedFont getLoadedFont();
	
}
