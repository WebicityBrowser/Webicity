package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.LineBreakValueParser;
import com.github.webicitybrowser.spec.css.property.text.LineBreakValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMLineBreakDeclarationParser implements CSSOMNamedDeclarationParser<LineBreakValue> {

	private final LineBreakValueParser lineBreakValueParser = new LineBreakValueParser();

	@Override
	public PropertyValueParser<LineBreakValue> getPropertyValueParser() {
		return lineBreakValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(LineBreakValue value) {
		LineBreak lineBreak = switch (value) {
			case AUTO -> LineBreak.AUTO;
			case LOOSE -> LineBreak.LOOSE;
			case NORMAL -> LineBreak.NORMAL;
			case STRICT -> LineBreak.STRICT;
			case ANYWHERE -> LineBreak.ANYWHERE;
		};

		return new Directive[] {
			LineBreakDirective.of(lineBreak)
		};
	}
	
}
