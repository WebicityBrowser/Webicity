package everyos.desktop.thready.core.graphics.color.converter;

import everyos.desktop.thready.core.graphics.color.RawColor;

public interface ColorConverter<Format> {
	
	Format fromRawColor(RawColor rawColor);
	
	RawColor toRawColor(Format format);
	
}
