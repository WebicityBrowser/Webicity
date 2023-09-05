package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexFactorValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFactorValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexFactorDeclarationParser implements CSSOMNamedDeclarationParser<FlexFactorValue> {

	private final PropertyValueParser<FlexFactorValue> parser = new FlexFactorValueParser();
	private final Function<Float, Directive> directiveFactory;

	public CSSOMFlexFactorDeclarationParser(Function<Float, Directive> directiveFactory) {
		this.directiveFactory = directiveFactory;
	}

	@Override
	public PropertyValueParser<FlexFactorValue> getPropertyValueParser() {
		return this.parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexFactorValue value) {
		return new Directive[] { directiveFactory.apply(value.value()) };
	}
	
}
