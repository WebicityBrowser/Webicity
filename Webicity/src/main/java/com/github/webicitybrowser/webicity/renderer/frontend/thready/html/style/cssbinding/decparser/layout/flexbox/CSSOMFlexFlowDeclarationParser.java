package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexFlowValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexFlowValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMFlexFlowDeclarationParser implements CSSOMNamedDeclarationParser<FlexFlowValue> {

	private final PropertyValueParser<FlexFlowValue> parser = new FlexFlowValueParser();

	@Override
	public PropertyValueParser<FlexFlowValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexFlowValue value) {
		List<Directive> directives = new ArrayList<>(2);
		if (value.flexDirection() != null) {
			directives.add(FlexDirectionDirective.of(CSSOMFlexDirectionDeclarationParser.convertFlexDirection(value.flexDirection())));
		}
		if (value.flexWrap() != null) {
			directives.add(FlexWrapDirective.of(CSSOMFlexWrapDeclarationParser.convertFlexWrap(value.flexWrap())));
		}

		return directives.toArray(new Directive[0]);
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FlexDirectionDirective.class, FlexWrapDirective.class);
	}
	
}
