package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.display.DisplayPropertyValueParser;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMDisplayDeclarationParser implements CSSOMNamedDeclarationParser<DisplayValue> {
	
	private final PropertyValueParser<DisplayValue> displayParser = new DisplayPropertyValueParser();
	
	@Override
	public PropertyValueParser<DisplayValue> getPropertyValueParser() {
		return this.displayParser;
	}

	@Override
	public Directive[] translatePropertyValue(DisplayValue value) {
		return new Directive[] {
			OuterDisplayDirective.of(convertOuterDisplayType(value.outerDisplayType()))
		};
	}

	private OuterDisplay convertOuterDisplayType(OuterDisplayType outerDisplayType) {
		switch (outerDisplayType) {
		case BLOCK:
			return OuterDisplay.BLOCK;
		case INLINE:
			return OuterDisplay.INLINE;
		case NONE:
			return OuterDisplay.NONE;
		case CONTENTS:
			return OuterDisplay.CONTENTS;
		default:
			throw new UnsupportedOperationException("Unsupported display type: " + outerDisplayType);
		}
	}
	
}
