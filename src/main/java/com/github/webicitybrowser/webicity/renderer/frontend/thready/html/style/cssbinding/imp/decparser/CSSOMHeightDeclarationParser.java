package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.sizing.WidthHeightValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.HeightDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size.SizeParser;

public class CSSOMHeightDeclarationParser implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PropertyValueParser<CSSValue> parser = new WidthHeightValueParser();

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		return new Directive[] {
			HeightDirective.of(SizeParser.parse(value))
		};
	}
	
}
