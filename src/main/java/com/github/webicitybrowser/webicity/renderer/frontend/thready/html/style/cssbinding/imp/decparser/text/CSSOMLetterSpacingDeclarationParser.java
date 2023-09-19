package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.LetterSpacingValueParser;
import com.github.webicitybrowser.spec.css.property.shared.length.LengthValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LetterSpacingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMLetterSpacingDeclarationParser implements CSSOMNamedDeclarationParser<LengthValue> {

	private final PropertyValueParser<LengthValue> letterSpacingParser = new LetterSpacingValueParser();

	@Override
	public PropertyValueParser<LengthValue> getPropertyValueParser() {
		return letterSpacingParser;
	}

	@Override
	public Directive[] translatePropertyValue(LengthValue value) {
		SizeCalculation letterSpacing = SizeParser.parseNonPercent(value);
		return new Directive[] {
			LetterSpacingDirective.of(letterSpacing)
		};
	}
	
}
