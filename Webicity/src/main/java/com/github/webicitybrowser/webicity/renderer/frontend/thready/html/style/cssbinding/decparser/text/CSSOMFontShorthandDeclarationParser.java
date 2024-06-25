package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.text;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.FontShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.font.FontValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flow.CSSOMLineHeightDeclarationParser;

public class CSSOMFontShorthandDeclarationParser implements CSSOMNamedDeclarationParser<FontValue> {

	private final FontShorthandValueParser fontParser = new FontShorthandValueParser();

	private final CSSOMFontFamilyDeclarationParser fontFamilyDeclarationParser = new CSSOMFontFamilyDeclarationParser();
	private final CSSOMFontSizeDeclarationParser fontSizeDeclarationParser = new CSSOMFontSizeDeclarationParser();
	private final CSSOMLineHeightDeclarationParser lineHeightDeclarationParser = new CSSOMLineHeightDeclarationParser();

	@Override
	public PropertyValueParser<FontValue> getPropertyValueParser() {
		return fontParser;
	}

	@Override
	public Directive[] translatePropertyValue(FontValue value) {
		List<Directive> directives = new ArrayList<>();
		directives.addAll(List.of(fontFamilyDeclarationParser.translatePropertyValue(value.fontFamilies())));
		value.fontSize().ifPresent(fontSize -> directives.addAll(List.of(fontSizeDeclarationParser.translatePropertyValue(fontSize))));
		value.lineHeight().ifPresent(lineHeight -> directives.addAll(List.of(lineHeightDeclarationParser.translatePropertyValue(lineHeight))));

		return directives.toArray(new Directive[0]);
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		List<Class<? extends Directive>> classes = new ArrayList<>();
		classes.addAll(fontFamilyDeclarationParser.getResultantDirectiveClasses());
		classes.addAll(fontSizeDeclarationParser.getResultantDirectiveClasses());
		classes.addAll(lineHeightDeclarationParser.getResultantDirectiveClasses());
		
		return classes;
	}
	
}
