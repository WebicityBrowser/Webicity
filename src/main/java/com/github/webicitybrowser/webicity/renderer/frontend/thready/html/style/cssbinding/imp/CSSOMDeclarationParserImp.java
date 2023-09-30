package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.util.TokenUtils;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMBackgroundColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMDisplayDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMFloatDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border.CSSOMBorderBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flexbox.CSSOMFlexBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin.CSSOMMarginBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.padding.CSSOMPaddingBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.position.CSSOMPositionBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size.CSSOMSizeBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.text.CSSOMTextBindings;

public class CSSOMDeclarationParserImp implements CSSOMDeclarationParser {
	
	private static final Logger logger = LoggerFactory.getLogger(CSSOMDeclarationParserImp.class);
	
	private final Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers = new HashMap<>();
	
	public CSSOMDeclarationParserImp() {
		namedDeclarationParsers.put("display", new CSSOMDisplayDeclarationParser());
		
		namedDeclarationParsers.put("color", new CSSOMColorDeclarationParser());
		namedDeclarationParsers.put("background-color", new CSSOMBackgroundColorDeclarationParser());
		namedDeclarationParsers.put("background", new CSSOMBackgroundColorDeclarationParser()); // TODO

		namedDeclarationParsers.put("float", new CSSOMFloatDeclarationParser());

		CSSOMBorderBindings.installTo(namedDeclarationParsers);
		CSSOMTextBindings.installTo(namedDeclarationParsers);
		CSSOMFlexBindings.installTo(namedDeclarationParsers);
		CSSOMPaddingBindings.installTo(namedDeclarationParsers);
		CSSOMSizeBindings.installTo(namedDeclarationParsers);
		CSSOMMarginBindings.installTo(namedDeclarationParsers);
		CSSOMPositionBindings.installTo(namedDeclarationParsers);
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
	private <T extends CSSValue> Directive[] invokeParser(CSSOMNamedDeclarationParser<?> namedParser, Declaration rule) {
		CSSOMNamedDeclarationParser<T> castedParser = (CSSOMNamedDeclarationParser<T>) namedParser;
		Optional<T> result = getResult(rule, castedParser.getPropertyValueParser());
		return result
			.map(value -> castedParser.translatePropertyValue(value))
			.orElse(null);
	}

	private <T extends CSSValue> Optional<T> getResult(Declaration rule, PropertyValueParser<T> parser) {
		TokenLike[] tokens = TokenUtils.stripWhitespace(rule.getValue());
		PropertyValueParseResult<T> result = parser.parse(tokens, 0, tokens.length);
		return result.getResult();
	}

}
