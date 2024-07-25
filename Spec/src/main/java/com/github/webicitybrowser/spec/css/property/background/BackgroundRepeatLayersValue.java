package com.github.webicitybrowser.spec.css.property.background;

import java.util.List;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record BackgroundRepeatLayersValue(List<BackgroundRepeatValue> repeats) implements CSSValue {
	
}
