package everyos.desktop.thready.core.graphics.color;

import everyos.desktop.thready.core.graphics.color.imp.InternalColorImp;

public final class Colors {
	
	private Colors() {}

	public static final RawColor BLACK = InternalColorImp.ofRGB8(0, 0, 0);
	public static final RawColor WHITE = InternalColorImp.ofRGB8(255, 255, 255);
	public static final RawColor DARK_GRAY = InternalColorImp.ofRGB8(102, 102, 102);
	public static final RawColor LIGHT_GRAY = InternalColorImp.ofRGB8(153, 153, 153);
	public static final RawColor RED = InternalColorImp.ofRGB8(255, 0, 0);
	public static final RawColor GREEN = InternalColorImp.ofRGB8(0, 255, 0);
	public static final RawColor BLUE = InternalColorImp.ofRGB8(0, 0, 255);
	
	public static final RawColor TRANSPARENT = InternalColorImp.ofRGBA8(0, 0, 0, 0);
	
	public static final RawColor DARK_GREY = DARK_GRAY;
	public static final RawColor LIGHT_GREY = LIGHT_GRAY;
	
}
