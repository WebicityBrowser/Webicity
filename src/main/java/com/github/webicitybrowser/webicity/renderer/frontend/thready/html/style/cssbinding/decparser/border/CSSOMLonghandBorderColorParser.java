package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.border;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.color.BorderColorLonghandValueParser;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.ColorParser;

public class CSSOMLonghandBorderColorParser<T extends Directive> implements CSSOMNamedDeclarationParser<ColorValue> {

	private final PropertyValueParser<ColorValue> longhandBorderColorValueParser = new BorderColorLonghandValueParser();
	private final Function<ColorFormat, T> directiveFactory;
	private final Class<T> directiveClass;

	public CSSOMLonghandBorderColorParser(Function<ColorFormat, T> directiveFactory, Class<T> directiveClass) {
		this.directiveFactory = directiveFactory;
		this.directiveClass = directiveClass;
	}

	@Override
	public PropertyValueParser<ColorValue> getPropertyValueParser() {
		return longhandBorderColorValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(ColorValue value) {
		ColorFormat color = ColorParser.parseColor(value);
		return new Directive[] {
			directiveFactory.apply(color)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(directiveClass);
	}
	
}
