package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.sizing.WidthHeightValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMDimensionDeclarationParser implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PropertyValueParser<CSSValue> parser = new WidthHeightValueParser();
	private final Function<SizeCalculation, Directive> directiveFactory;

	public CSSOMDimensionDeclarationParser(Function<SizeCalculation, Directive> directiveFactory) {
		this.directiveFactory = directiveFactory;
	}

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		return new Directive[] {
			directiveFactory.apply(SizeParser.parseWithBoxPercents(value))
		};
	}
	
}
