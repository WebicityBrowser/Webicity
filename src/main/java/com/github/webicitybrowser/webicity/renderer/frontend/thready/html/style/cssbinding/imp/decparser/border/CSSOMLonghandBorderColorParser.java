package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.color.BorderColorLonghandValueParser;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.ColorParser;

public class CSSOMLonghandBorderColorParser implements CSSOMNamedDeclarationParser<ColorValue> {

	private final PropertyValueParser<ColorValue> longhandBorderColorValueParser = new BorderColorLonghandValueParser();
	private final Function<ColorFormat, Directive> directiveFactory;

	public CSSOMLonghandBorderColorParser(Function<ColorFormat, Directive> directiveFactory) {
		this.directiveFactory = directiveFactory;
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
	
}
