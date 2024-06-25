package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.padding;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.padding.PaddingLonghandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.SizeParser;

public class CSSOMLonghandPaddingDeclarationParser<T extends Directive> implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PaddingLonghandValueParser longhandPaddingValueParser = new PaddingLonghandValueParser();
	private final Function<SizeCalculation, ? extends Directive> directiveFactory;
	private final Class<T> directiveClass;

	public CSSOMLonghandPaddingDeclarationParser(Function<SizeCalculation, T> directiveFactory, Class<T> directiveClass) {
		this.directiveFactory = directiveFactory;
		this.directiveClass = directiveClass;
	}

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return longhandPaddingValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		SizeCalculation sizeCalculation = SizeParser.parseWithBoxPercents(value);
		return new Directive[] {
			directiveFactory.apply(sizeCalculation)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(directiveClass);
	}
	
}
