package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.text.TextAlignValueParser;
import com.github.webicitybrowser.spec.css.property.text.TextAlignValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective.TextAlign;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMTextAlignDeclarationParser  implements CSSOMNamedDeclarationParser<TextAlignValue> {

	private final PropertyValueParser<TextAlignValue> textAlignValueParser = new TextAlignValueParser();

	@Override
	public PropertyValueParser<TextAlignValue> getPropertyValueParser() {
		return textAlignValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(TextAlignValue value) {
		TextAlign textAlign = switch (value) {
		case START -> TextAlign.START;
		case END -> TextAlign.END;
		case LEFT -> TextAlign.LEFT;
		case RIGHT -> TextAlign.RIGHT;
		case CENTER -> TextAlign.CENTER;
		case JUSTIFY -> TextAlign.JUSTIFY;
		case MATCH_PARENT -> TextAlign.MATCH_PARENT;
		case JUSTIFY_ALL -> TextAlign.JUSTIFY_ALL;
		};

		return new Directive[] {
			TextAlignDirective.of(textAlign)
		};
	}
	
}
