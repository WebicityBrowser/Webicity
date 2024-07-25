package com.github.webicitybrowser.spec.css.property.background;

import java.util.List;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public record BackgroundValue(List<BackgroundLayer> layer) implements CSSValue {
	
	public static record BackgroundLayer(ColorValue color) {}

}
