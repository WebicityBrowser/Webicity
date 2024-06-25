package com.github.webicitybrowser.spec.css.property.fontfamily;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public interface FontFamilyValue extends CSSValue {

	FontFamilyEntry[] getEntries();

	interface FontFamilyEntry {}

}
