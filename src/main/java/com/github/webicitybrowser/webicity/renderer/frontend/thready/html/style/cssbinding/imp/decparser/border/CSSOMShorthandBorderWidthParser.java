package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.width.BorderWidthShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.border.width.BorderWidthValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMShorthandBorderWidthParser implements CSSOMNamedDeclarationParser<BorderWidthValue> {

	private final BorderWidthShorthandValueParser shorthandParser = new BorderWidthShorthandValueParser();

	@Override
	public PropertyValueParser<BorderWidthValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(BorderWidthValue value) {
		return new Directive[] {
			BorderWidthDirective.ofLeft(getSizeCalculation(value.left())),
			BorderWidthDirective.ofRight(getSizeCalculation(value.right())),
			BorderWidthDirective.ofTop(getSizeCalculation(value.top())),
			BorderWidthDirective.ofBottom(getSizeCalculation(value.bottom()))
		};
	}

	public static SizeCalculation getSizeCalculation(CSSValue value) {
		return SizeParser.parseNonPercent(value);
	}
	
}
