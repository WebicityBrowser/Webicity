package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser;

import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;

public final class ColorParser {
	
	private ColorParser() {}

	public static ColorFormat parseColor(ColorValue colorValue) {
		return createColorFrom(colorValue);
	}

	private static ColorFormat createColorFrom(ColorValue value) {
		return new RGBA8Color(value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha());
	}

}
