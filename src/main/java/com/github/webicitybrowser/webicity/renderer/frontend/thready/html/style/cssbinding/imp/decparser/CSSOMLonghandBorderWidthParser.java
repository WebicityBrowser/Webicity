package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.width.BorderWidthLonghandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size.SizeParser;

public class CSSOMLonghandBorderWidthParser implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PropertyValueParser<CSSValue> longhandBorderWidthValueParser = new BorderWidthLonghandValueParser();
	private final Function<SizeCalculation, Directive> directiveFactory;

	public CSSOMLonghandBorderWidthParser(Function<SizeCalculation, Directive> directiveFactory) {
		this.directiveFactory = directiveFactory;
	}

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return longhandBorderWidthValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		SizeCalculation sizeCalculation = SizeParser.parseNonPercent(value);
		return new Directive[] {
			directiveFactory.apply(sizeCalculation)
		};
	}
	
}
