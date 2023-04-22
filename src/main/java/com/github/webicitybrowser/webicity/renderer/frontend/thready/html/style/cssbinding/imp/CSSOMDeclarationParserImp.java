package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;

public class CSSOMDeclarationParserImp implements CSSOMDeclarationParser {
	
	private static final Logger logger = LoggerFactory.getLogger(CSSOMDeclarationParserImp.class);
	
	private final Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers = new HashMap<>();
	
	public CSSOMDeclarationParserImp() {
		namedDeclarationParsers.put("color", new CSSOMColorDeclarationParser());
		namedDeclarationParsers.put("display", new CSSOMDisplayDeclarationParser());
	}
	
	@Override
	public Directive[] parseDeclaration(Declaration rule) {
		CSSOMNamedDeclarationParser<?> namedParser = namedDeclarationParsers.get(rule.getName());
		if (namedParser == null) {
			logger.warn("Unrecognized declaration name: " + rule.getName());
			return null;
		}
		
		return invokeParser(namedParser, rule);
	}

	@SuppressWarnings("unchecked")
	private <T> Directive[] invokeParser(CSSOMNamedDeclarationParser<?> namedParser, Declaration rule) {
		CSSOMNamedDeclarationParser<T> castedParser = (CSSOMNamedDeclarationParser<T>) namedParser;
		Optional<T> result = getResult(rule, castedParser.getPropertyValueParser());
		return result
			.map(value -> castedParser.translatePropertyValue(value))
			.orElse(null);
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
