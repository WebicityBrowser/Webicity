package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexWrapValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexWrapValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexWrapDeclarationParser implements CSSOMNamedDeclarationParser<FlexWrapValue> {

	private final FlexWrapValueParser parser = new FlexWrapValueParser();

	@Override
	public PropertyValueParser<FlexWrapValue> getPropertyValueParser() {
		return this.parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexWrapValue value) {
		FlexWrap flexWrap = convertFlexWrap(value);
		return new Directive[] { FlexWrapDirective.of(flexWrap) };
	}

	private FlexWrap convertFlexWrap(FlexWrapValue value) {
		return switch(value) {
			case NOWRAP -> FlexWrap.NOWRAP;
			case WRAP -> FlexWrap.WRAP;
			case WRAP_REVERSE -> FlexWrap.WRAP_REVERSE;
			default -> throw new UnsupportedOperationException("Unsupported flex wrap: " + value);
		};
	}

}
