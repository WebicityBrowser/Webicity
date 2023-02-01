package everyos.desktop.thready.core.graphics.canvas.imp;

import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public class Paint2DBuilder {
	
	private ColorFormat color;

	private LoadedFont loadedFont;
	
	public Paint2DBuilder setColor(ColorFormat color) {
		this.color = color;

		return this;
	}
	
	public ColorFormat getColor() {
		return this.color;
	}
	
	public Paint2DBuilder setLoadedFont(LoadedFont loadedFont) {
		this.loadedFont = loadedFont;

		return this;
	}
	
	public LoadedFont getLoadedFont() {
		return this.loadedFont;
	}
	
	public Paint2D build() {
		return new Paint2DImp(this);
	}
	
	public static Paint2DBuilder clone(Paint2D paint) {
		return new Paint2DBuilder()
			.setColor(paint.getColor())
			.setLoadedFont(paint.getLoadedFont());
	}
	
}
