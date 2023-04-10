package com.github.webicitybrowser.thready.drawing.skija.imp;

import java.util.Map;
import java.util.WeakHashMap;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;

public class SkijaResourceLoaderImp implements ResourceLoader {
	
	// TODO: Make a SoftHashMap
	private final static Map<FontSettings, Font2D> fonts = new WeakHashMap<>();

	@Override
	public Image loadResource(ImageSource source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font2D loadFont(FontSettings settings) {
		return fonts.computeIfAbsent(settings, _1 -> SkijaFont2DImp.createFor(settings));
	}

}
