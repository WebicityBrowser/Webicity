package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexJustifyContentValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexJustifyContentValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMFlexJustifyContentDeclarationParser implements CSSOMNamedDeclarationParser<FlexJustifyContentValue> {

	private final PropertyValueParser<FlexJustifyContentValue> parser = new FlexJustifyContentValueParser();

	@Override
	public PropertyValueParser<FlexJustifyContentValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexJustifyContentValue value) {
		FlexJustifyContent justifyContent = switch (value) {
			case CENTER -> FlexJustifyContent.CENTER;
			case FLEX_END -> FlexJustifyContent.FLEX_END;
			case FLEX_START -> FlexJustifyContent.FLEX_START;
			case SPACE_AROUND -> FlexJustifyContent.SPACE_AROUND;
			case SPACE_BETWEEN -> FlexJustifyContent.SPACE_BETWEEN;
			default -> throw new IllegalStateException("Unexpected value: " + value);
		};

		return new Directive[] { FlexJustifyContentDirective.of(justifyContent) };
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FlexJustifyContentDirective.class);
	}
	
}
