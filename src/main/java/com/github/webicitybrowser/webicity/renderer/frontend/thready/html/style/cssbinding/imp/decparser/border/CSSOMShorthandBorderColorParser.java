package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.color.BorderColorShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.border.color.BorderColorValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.ColorParser;

public class CSSOMShorthandBorderColorParser implements CSSOMNamedDeclarationParser<BorderColorValue> {

	private final BorderColorShorthandValueParser shorthandParser = new BorderColorShorthandValueParser();

	@Override
	public PropertyValueParser<BorderColorValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(BorderColorValue value) {
		return new Directive[] {
			BorderColorDirective.ofLeft(getColor(value.left())),
			BorderColorDirective.ofRight(getColor(value.right())),
			BorderColorDirective.ofTop(getColor(value.top())),
			BorderColorDirective.ofBottom(getColor(value.bottom()))
		};
	}

	public static ColorFormat getColor(ColorValue value) {
		return ColorParser.parseColor(value);
	}
	
}
