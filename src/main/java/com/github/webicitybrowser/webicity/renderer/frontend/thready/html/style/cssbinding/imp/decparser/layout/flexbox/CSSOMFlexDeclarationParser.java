package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexGrowDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexShrinkDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFlexDeclarationParser implements CSSOMNamedDeclarationParser<FlexValue> {

	private PropertyValueParser<FlexValue> parser = new FlexShorthandValueParser();

	@Override
	public PropertyValueParser<FlexValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexValue value) {
		List<Directive> directives = new ArrayList<>();

		if (value.growFactor() != null) {
			directives.add(FlexGrowDirective.of(value.growFactor().value()));
		}
		if (value.shrinkFactor() != null) {
			directives.add(FlexShrinkDirective.of(value.shrinkFactor().value()));
		}

		// TODO: basis
		
		return directives.toArray(Directive[]::new);
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FlexGrowDirective.class, FlexShrinkDirective.class);
	}
	
}
