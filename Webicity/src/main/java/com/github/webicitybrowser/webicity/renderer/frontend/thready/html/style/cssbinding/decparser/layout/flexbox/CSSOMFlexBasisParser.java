package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flex.FlexBasisValueParser;
import com.github.webicitybrowser.spec.css.property.flexbox.FlexBasisValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexBasisDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.SizeParser;

public class CSSOMFlexBasisParser implements CSSOMNamedDeclarationParser<FlexBasisValue> {

	private final PropertyValueParser<FlexBasisValue> parser = new FlexBasisValueParser();

	@Override
	public PropertyValueParser<FlexBasisValue> getPropertyValueParser() {
		return parser;
	}

	@Override
	public Directive[] translatePropertyValue(FlexBasisValue value) {
		return new Directive[] {
			new FlexBasisDirective(SizeParser.parseNonPercent(value.size()), value.isAuto())
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FlexBasisDirective.class);
	}
	
}
