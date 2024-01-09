package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.position;

import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.position.PositionOffsetValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMPositionOffsetDeclarationParser<T extends Directive> implements CSSOMNamedDeclarationParser<CSSValue> {

	private final PositionOffsetValueParser positionOffsetValueParser = new PositionOffsetValueParser();
	private final Function<SizeCalculation, T> directiveFactory;
	private final Class<T> directiveClass;

	public CSSOMPositionOffsetDeclarationParser(Function<SizeCalculation, T> directiveFactory, Class<T> directiveClass) {
		this.directiveFactory = directiveFactory;
		this.directiveClass = directiveClass;
	}

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return positionOffsetValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		SizeCalculation sizeCalculation = value instanceof AutoValue ?
			SizeCalculation.SIZE_AUTO :
			SizeParser.parseWithBoxPercents(value);
		
		return new Directive[] {
			directiveFactory.apply(sizeCalculation)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(directiveClass);
	}
	
}
