package everyos.desktop.thready.core.graphics.color.converter;

import everyos.desktop.thready.core.graphics.color.RawColor;
import everyos.desktop.thready.core.graphics.color.colors.RGBA8Color;
import everyos.desktop.thready.core.graphics.color.formats.RGBA8ColorFormat;
import everyos.desktop.thready.core.graphics.color.imp.InternalColorImp;

public class RGBA8ColorConverter implements ColorConverter<RGBA8ColorFormat> {

	@Override
	public RGBA8ColorFormat fromRawColor(RawColor rawColor) {
		InternalColorImp color = (InternalColorImp) rawColor;
		return new RGBA8Color(color.getRed8(), color.getGreen8(), color.getBlue8(), color.getAlpha8());
	}

	@Override
	public RawColor toRawColor(RGBA8ColorFormat color) {
		return InternalColorImp.ofRGBA8(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

}
