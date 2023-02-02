package everyos.desktop.thready.renderer.skija;

import java.util.Map;
import java.util.WeakHashMap;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.graphics.image.Image;
import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.LoadedFont;

public class SkijaResourceGenerator implements ResourceGenerator {

	// TODO: Make a SoftHashMap
	private final static Map<FontInfo, LoadedFont> fonts = new WeakHashMap<>();
	
	@Override
	public LoadedFont loadFont(FontInfo fontInfo) {
		return fonts.computeIfAbsent(fontInfo, info -> SkijaLoadedFont.createFor(info));
	}

	@Override
	public LoadedImage loadImage(Image image) {
		// TODO Auto-generated method stub
		return null;
	}

}
