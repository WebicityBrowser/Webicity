package everyos.desktop.thready.core.graphics.canvas.imp;

import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.color.RawColor;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public class Paint2DImp implements Paint2D {
	
	private final RawColor color;
	
	private final LoadedFont loadedFont;

	public Paint2DImp(Paint2DBuilder builder) {
		this.color = builder.getColor();
		this.loadedFont = builder.getLoadedFont();
	}

	@Override
	public RawColor getColor() {
		return this.color;
	}

	@Override
	public LoadedFont getLoadedFont() {
		return this.loadedFont;
	}

}
