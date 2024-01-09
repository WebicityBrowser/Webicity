package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.width.BorderWidthLonghandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMLonghandBorderWidthParser<T extends Directive> implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PropertyValueParser<CSSValue> longhandBorderWidthValueParser = new BorderWidthLonghandValueParser();
	private final Function<SizeCalculation, T> directiveFactory;
	private final Class<T> directiveClass;

	public CSSOMLonghandBorderWidthParser(Function<SizeCalculation, T> directiveFactory, Class<T> directiveClass) {
		this.directiveFactory = directiveFactory;
		this.directiveClass = directiveClass;
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

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(directiveClass);
	}
	
}
