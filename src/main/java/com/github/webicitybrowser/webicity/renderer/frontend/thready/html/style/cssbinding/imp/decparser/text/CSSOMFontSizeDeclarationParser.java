package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.FontSizeValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontSizeDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMFontSizeDeclarationParser implements CSSOMNamedDeclarationParser<CSSValue> {

	private final FontSizeValueParser fontSizeValueParser = new FontSizeValueParser();

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return fontSizeValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		return new Directive[] {
			FontSizeDirective.of(SizeParser.parseWithFontPercents(value))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FontSizeDirective.class);
	}
	
}
