package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.color.ColorPropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.display.DisplayPropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.spec.css.property.display.DisplayValue;
import com.github.webicitybrowser.spec.css.property.display.OuterDisplayType;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.directive.ForegroundColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;

public class CSSOMDeclarationParserImp implements CSSOMDeclarationParser {

	private final PropertyValueParser<ColorValue> colorParser = new ColorPropertyValueParser();
	private final PropertyValueParser<DisplayValue> displayParser = new DisplayPropertyValueParser();
	
	// TODO: HashMap of dedicated parser classes
	
	@Override
	public Directive[] parseDeclaration(Declaration rule) {
		switch (rule.getName()) {
		case "color":
			return parseColorRule(rule);
		case "display":
			return parseDisplayRule(rule);
		default:
			// TODO: Log unrecognized declarations
			return null;
		}
	}

	private Directive[] parseColorRule(Declaration rule) {
		return getResult(rule, colorParser)
			.map(value -> new Directive[] { ForegroundColorDirective.of(createColorFrom(value)) })
			.orElse(null);
	}

	private ColorFormat createColorFrom(ColorValue value) {
		return new RGBA8Color(value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha());
	}
	
	private Directive[] parseDisplayRule(Declaration rule) {
		return getResult(rule, displayParser)
			.map(value -> new Directive[] {
				OuterDisplayDirective.of(convertOuterDisplayType(value.outerDisplayType()))
			})
			.orElse(null);
	}

	private OuterDisplay convertOuterDisplayType(OuterDisplayType outerDisplayType) {
		switch (outerDisplayType) {
		case BLOCK:
			return OuterDisplay.BLOCK;
		case INLINE:
			return OuterDisplay.INLINE;
		default:
			throw new UnsupportedOperationException("Unsupported display type: " + outerDisplayType);
		}
	}

	private <T> Optional<T> getResult(Declaration rule, PropertyValueParser<T> parser) {
		TokenLike[] tokens = stripWhitespace(rule.getValue());
		PropertyValueParseResult<T> result = parser.parse(tokens, 0, tokens.length);
		return result.getResult();
	}

	private TokenLike[] stripWhitespace(TokenLike[] value) {
		int leading = 0;
		while (leading < value.length && value[leading] instanceof WhitespaceToken) {
			leading++;
		}
		
		int trailing = 0;
		while (trailing > leading && value[trailing] instanceof WhitespaceToken) {
			trailing++;
		}
		
		int newLength = value.length - trailing - leading;
		TokenLike[] sanitized = new TokenLike[newLength];
		System.arraycopy(value, leading, sanitized, 0, newLength);
		
		return sanitized;
	}

}