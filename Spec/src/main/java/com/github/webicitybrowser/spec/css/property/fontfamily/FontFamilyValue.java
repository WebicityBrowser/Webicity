package com.github.webicitybrowser.spec.css.property.fontfamily;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public record FontFamilyValue(FontFamilyEntry[] entries) implements CSSValue {

	public interface FontFamilyEntry {}

}
