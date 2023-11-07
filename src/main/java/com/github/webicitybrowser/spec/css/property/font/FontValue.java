package com.github.webicitybrowser.spec.css.property.font;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue;

public record FontValue(FontFamilyValue fontFamilies, Optional<CSSValue> fontSize, Optional<CSSValue> lineHeight) implements CSSValue {

}
