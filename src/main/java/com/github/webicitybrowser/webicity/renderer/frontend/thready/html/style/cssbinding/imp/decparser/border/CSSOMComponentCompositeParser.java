package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.composite.BorderCompositeValueParser;
import com.github.webicitybrowser.spec.css.property.border.BorderCompositeValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.ColorParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMComponentCompositeParser implements CSSOMNamedDeclarationParser<BorderCompositeValue> {

	private final PropertyValueParser<BorderCompositeValue> longhandBorderColorValueParser = new BorderCompositeValueParser();

	private final Function<ColorFormat, Directive> colorDirectiveFactory;
	private final Function<SizeCalculation, Directive> sizeDirectiveFactory;

	public CSSOMComponentCompositeParser(
		Function<ColorFormat, Directive> colorDirectiveFactory,
		Function<SizeCalculation, Directive> sizeDirectiveFactory
	) {
		this.colorDirectiveFactory = colorDirectiveFactory;
		this.sizeDirectiveFactory = sizeDirectiveFactory;
	}

	@Override
	public PropertyValueParser<BorderCompositeValue> getPropertyValueParser() {
		return longhandBorderColorValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(BorderCompositeValue value) {
		List<Directive> directives = new ArrayList<>();
		if (value.color() != null) {
			directives.add(colorDirectiveFactory.apply(ColorParser.parseColor(value.color())));
		}
		if (value.width() != null) {
			directives.add(sizeDirectiveFactory.apply(SizeParser.parseNonPercent(value.width())));
		}

		return directives.toArray(Directive[]::new);
	}
	
}
