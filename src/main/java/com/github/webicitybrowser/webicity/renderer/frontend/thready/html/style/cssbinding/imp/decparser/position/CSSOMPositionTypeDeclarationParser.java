package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.position;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.position.PositionTypeValueParser;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionTypeDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionTypeValue;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMPositionTypeDeclarationParser implements CSSOMNamedDeclarationParser<PositionTypeValue> {

	private final PositionTypeValueParser positionTypeValueParser = new PositionTypeValueParser();

	@Override
	public PropertyValueParser<PositionTypeValue> getPropertyValueParser() {
		return positionTypeValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(PositionTypeValue value) {
		PositionType positionType = switch (value) {
			case STATIC -> PositionType.STATIC;
			case RELATIVE -> PositionType.RELATIVE;
			case ABSOLUTE -> PositionType.ABSOLUTE;
			case STICKY -> PositionType.STICKY;
			case FIXED -> PositionType.FIXED;
		};

		return new Directive[] {
			PositionTypeDirective.of(positionType)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(PositionTypeDirective.class);
	}
	
}
