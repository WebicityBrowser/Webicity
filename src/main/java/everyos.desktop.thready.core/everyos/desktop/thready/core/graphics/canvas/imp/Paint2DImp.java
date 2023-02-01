package everyos.desktop.thready.core.graphics.canvas.imp;

import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public class Paint2DImp implements Paint2D {
	
	private final ColorFormat color;
	
	private final LoadedFont loadedFont;

	public Paint2DImp(Paint2DBuilder builder) {
		this.color = builder.getColor();
		this.loadedFont = builder.getLoadedFont();
	}

	@Override
	public ColorFormat getColor() {
		return this.color;
	}

	@Override
	public LoadedFont getLoadedFont() {
		return this.loadedFont;
	}

}
