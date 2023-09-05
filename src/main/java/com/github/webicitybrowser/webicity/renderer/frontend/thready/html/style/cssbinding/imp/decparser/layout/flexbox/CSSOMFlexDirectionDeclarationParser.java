package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexDirectionValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexDirectionValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexDirectionDeclarationParser implements CSSOMNamedDeclarationParser<FlexDirectionValue> {

	private final FlexDirectionValueParser parser = new FlexDirectionValueParser();

	@Override
	public PropertyValueParser<FlexDirectionValue> getPropertyValueParser() {
		return this.parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexDirectionValue value) {
		FlexDirection flexDirection = convertFlexDirection(value);
		return new Directive[] { FlexDirectionDirective.of(flexDirection) };
	}

	private FlexDirection convertFlexDirection(FlexDirectionValue value) {
		return switch(value) {
			case ROW -> FlexDirection.ROW;
			case ROW_REVERSE -> FlexDirection.ROW_REVERSE;
			case COLUMN -> FlexDirection.COLUMN;
			case COLUMN_REVERSE -> FlexDirection.COLUMN_REVERSE;
			default -> throw new UnsupportedOperationException("Unsupported flex direction: " + value);
		};
	}

}
