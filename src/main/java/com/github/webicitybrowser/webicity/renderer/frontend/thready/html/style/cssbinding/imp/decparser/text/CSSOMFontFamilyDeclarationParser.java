package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.FontFamilyValueParser;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue.FontFamilyEntry;
import com.github.webicitybrowser.spec.css.property.fontfamily.NamedFontFamilyEntry;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontFamilyDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMFontFamilyDeclarationParser implements CSSOMNamedDeclarationParser<FontFamilyValue> {

	private final FontFamilyValueParser fontFamilyParser = new FontFamilyValueParser();

	@Override
	public PropertyValueParser<FontFamilyValue> getPropertyValueParser() {
		return fontFamilyParser;
	}

	@Override
	public Directive[] translatePropertyValue(FontFamilyValue value) {
		FontSource[] fontSources = new FontSource[value.getEntries().length];
		for (int i = 0; i < value.getEntries().length; i++) {
			fontSources[i] = translateFontFamilyEntry(value.getEntries()[i]);
		}

		return new Directive[] { FontFamilyDirective.of(fontSources) };
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(FontFamilyDirective.class);
	}

	private FontSource translateFontFamilyEntry(FontFamilyEntry fontFamilyEntry) {
		if (fontFamilyEntry instanceof NamedFontFamilyEntry) {
			NamedFontFamilyEntry namedFontFamilyEntry = (NamedFontFamilyEntry) fontFamilyEntry;
			return new NamedFontSource(namedFontFamilyEntry.getName());
		} else {
			throw new IllegalArgumentException("Unknown FontFamilyEntry: " + fontFamilyEntry);
		}
	}
	
}
