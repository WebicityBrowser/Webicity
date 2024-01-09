package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexFactorValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFactorValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexFactorDeclarationParser<T extends Directive> implements CSSOMNamedDeclarationParser<FlexFactorValue> {

	private final PropertyValueParser<FlexFactorValue> parser = new FlexFactorValueParser();
	private final Function<Float, T> directiveFactory;
	private final Class<T> directiveClass;

	public CSSOMFlexFactorDeclarationParser(Function<Float, T> directiveFactory, Class<T> directiveClass) {
		this.directiveFactory = directiveFactory;
		this.directiveClass = directiveClass;
	}

	@Override
	public PropertyValueParser<FlexFactorValue> getPropertyValueParser() {
		return this.parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexFactorValue value) {
		return new Directive[] { directiveFactory.apply(value.value()) };
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(directiveClass);
	}
	
}
