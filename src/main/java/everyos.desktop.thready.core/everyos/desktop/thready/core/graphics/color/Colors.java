package everyos.desktop.thready.core.graphics.color;

import everyos.desktop.thready.core.graphics.color.colors.RGBA8Color;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;

public final class Colors {
	
	private Colors() {}

	public static final ColorFormat BLACK = new RGBA8Color(0, 0, 0);
	public static final ColorFormat WHITE = new RGBA8Color(255, 255, 255);
	public static final ColorFormat DARK_GRAY = new RGBA8Color(102, 102, 102);
	public static final ColorFormat LIGHT_GRAY = new RGBA8Color(153, 153, 153);
	public static final ColorFormat RED = new RGBA8Color(255, 0, 0);
	public static final ColorFormat GREEN = new RGBA8Color(0, 255, 0);
	public static final ColorFormat BLUE = new RGBA8Color(0, 0, 255);
	
	public static final ColorFormat TRANSPARENT = new RGBA8Color(0, 0, 0, 0);
	
	public static final ColorFormat DARK_GREY = DARK_GRAY;
	public static final ColorFormat LIGHT_GREY = LIGHT_GRAY;
	
}
