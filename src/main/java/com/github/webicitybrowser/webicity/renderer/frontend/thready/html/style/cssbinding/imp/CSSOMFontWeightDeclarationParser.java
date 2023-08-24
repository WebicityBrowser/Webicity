package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.parser.property.fontweight.FontWeightValueParser;
import com.github.webicitybrowser.spec.css.property.fontweight.FontWeightValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.FontWeightDirective;

public class CSSOMFontWeightDeclarationParser implements CSSOMNamedDeclarationParser<FontWeightValue> {

	private final FontWeightValueParser fontWeightValueParser = new FontWeightValueParser();

	@Override
	public FontWeightValueParser getPropertyValueParser() {
		return this.fontWeightValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(FontWeightValue value) {
		return new Directive[] { FontWeightDirective.of(value) };
	}
	
}
