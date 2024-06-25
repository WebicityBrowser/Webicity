package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.floatbox.FloatValueParser;
import com.github.webicitybrowser.spec.css.property.floatbox.FloatValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMFloatDeclarationParser implements CSSOMNamedDeclarationParser<FloatValue> {

	private final FloatValueParser floatValueParser = new FloatValueParser();

	@Override
	public PropertyValueParser<FloatValue> getPropertyValueParser() {
		return floatValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(FloatValue value) {
		FloatDirection floatDirection = switch (value) {
			case LEFT -> FloatDirection.LEFT;
			case RIGHT -> FloatDirection.RIGHT;
			case NONE -> FloatDirection.NONE;
		};

		return new Directive[] {
			FloatDirective.of(floatDirection)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FloatDirective.class);
	}
	
}
