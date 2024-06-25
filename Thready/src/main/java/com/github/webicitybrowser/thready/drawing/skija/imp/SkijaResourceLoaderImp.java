package com.github.webicitybrowser.thready.drawing.skija.imp;

import java.util.Map;
import java.util.WeakHashMap;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.image.EncodedBytesImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.RasterBytesImageSource;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;

import io.github.humbleui.skija.ColorAlphaType;
import io.github.humbleui.skija.ColorInfo;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.ImageInfo;

public class SkijaResourceLoaderImp implements ResourceLoader {
	
	// TODO: Make a SoftHashMap
	private final static Map<FontSettings, Font2D> fonts = new WeakHashMap<>();

	@Override
	public Image loadImage(ImageSource source) {
		
		io.github.humbleui.skija.Image loadedImage;
		if (source instanceof EncodedBytesImageSource encodedBytesSource) {
			byte[] data = encodedBytesSource.bytes();
			loadedImage = io.github.humbleui.skija.Image.makeDeferredFromEncodedBytes(data);
		} else if (source instanceof RasterBytesImageSource rasterBytesSource) {
			byte[] data = rasterBytesSource.raster();
			ImageInfo info = new ImageInfo(
				new ColorInfo(ColorType.RGBA_8888, ColorAlphaType.UNPREMUL, ColorSpace.getSRGB()),
				rasterBytesSource.width(), rasterBytesSource.height());
			loadedImage = io.github.humbleui.skija.Image.makeRasterFromBytes(info, data, rasterBytesSource.width() * 4);
		} else {
			throw new UnsupportedOperationException("Unknown image source type: " + source.getClass());
		}

		return new SkijaImage(loadedImage);
	}

	@Override
	public Font2D loadFont(FontSettings settings) {
		return fonts.computeIfAbsent(settings, _1 -> SkijaFont2DImp.createFor(settings));
	}

}
