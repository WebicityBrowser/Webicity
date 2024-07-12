package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.floatbox;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.floatbox.ClearValueParser;
import com.github.webicitybrowser.spec.css.property.floatbox.ClearValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.ClearDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.ClearDirection;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMClearDeclarationParser implements CSSOMNamedDeclarationParser<ClearValue> {

	private final ClearValueParser clearValueParser = new ClearValueParser();

	@Override
	public PropertyValueParser<ClearValue> getPropertyValueParser() {
		return clearValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(ClearValue value) {
		ClearDirection clearDirection = switch (value) {
			case BOTH -> ClearDirection.BOTH;
			case LEFT -> ClearDirection.LEFT;
			case RIGHT -> ClearDirection.RIGHT;
			case NONE -> ClearDirection.NONE;
		};

		return new Directive[] {
			ClearDirective.of(clearDirection)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(ClearDirective.class);
	}
	
}
