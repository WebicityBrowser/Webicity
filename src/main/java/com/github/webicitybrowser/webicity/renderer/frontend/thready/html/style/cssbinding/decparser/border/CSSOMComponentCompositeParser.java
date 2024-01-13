package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.border;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.composite.BorderCompositeValueParser;
import com.github.webicitybrowser.spec.css.property.border.BorderCompositeValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.ColorParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.SizeParser;

public class CSSOMComponentCompositeParser<T extends Directive, V extends Directive> implements CSSOMNamedDeclarationParser<BorderCompositeValue> {

	private final PropertyValueParser<BorderCompositeValue> longhandBorderColorValueParser = new BorderCompositeValueParser();

	private final Function<ColorFormat, T> colorDirectiveFactory;
	private final Class<T> colorDirectiveClass;
	private final Function<SizeCalculation, V> sizeDirectiveFactory;
	private final Class<V> sizeDirectiveClass;

	public CSSOMComponentCompositeParser(
		Function<ColorFormat, T> colorDirectiveFactory,
		Class<T> colorDirectiveClass,
		Function<SizeCalculation, V> sizeDirectiveFactory,
		Class<V> sizeDirectiveClass
	) {
		this.colorDirectiveFactory = colorDirectiveFactory;
		this.colorDirectiveClass = colorDirectiveClass;
		this.sizeDirectiveFactory = sizeDirectiveFactory;
		this.sizeDirectiveClass = sizeDirectiveClass;
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

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(colorDirectiveClass, sizeDirectiveClass);
	}
	
}
