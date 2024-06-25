package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.CSSOMBackgroundColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.CSSOMColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.CSSOMDisplayDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.CSSOMFloatDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.border.CSSOMBorderBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flexbox.CSSOMFlexBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.layout.flow.CSSOMFlowBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.margin.CSSOMMarginBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.padding.CSSOMPaddingBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.position.CSSOMPositionBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.size.CSSOMSizeBindings;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.text.CSSOMTextBindings;

public class CSSOMDeclarationParserImp implements CSSOMDeclarationParser {
	
	private static final Logger logger = LoggerFactory.getLogger(CSSOMDeclarationParserImp.class);
	private static final Directive[] EMPTY_DIRECTIVE_ARRAY = new Directive[0];
	
	private final Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers = new HashMap<>();
	private final Map<Class<? extends Directive>, List<String>> directivePropertyNames = new HashMap<>();
	
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
		CSSOMFlowBindings.installTo(namedDeclarationParsers);

		generateDirectivePropertyNameMap();
	}

	@Override
	public Directive[] parseDeclaration(String name, TokenLike[] tokens) {
		CSSOMNamedDeclarationParser<?> namedParser = namedDeclarationParsers.get(name);
		if (namedParser == null) {
			logger.warn("Unrecognized declaration name: " + name);
			return EMPTY_DIRECTIVE_ARRAY;
		}
		
		return invokeParser(namedParser, tokens);
	}

	@Override
	public CSSOMNamedDeclarationParser<?> getNamedDeclarationParser(String propertyName) {
		return namedDeclarationParsers.get(propertyName);
	}

	@Override
	public List<String> getDirectivePropertyNames(Class<? extends Directive> directiveClass) {
		return directivePropertyNames.getOrDefault(directiveClass, List.of());	
	}

	private void generateDirectivePropertyNameMap() {
		for (Map.Entry<String, CSSOMNamedDeclarationParser<?>> entry : namedDeclarationParsers.entrySet()) {
			CSSOMNamedDeclarationParser<?> parser = entry.getValue();
			for (Class<? extends Directive> directiveClass : parser.getResultantDirectiveClasses()) {
				directivePropertyNames
					.computeIfAbsent(directiveClass, _1 -> new ArrayList<>(1))
					.add(entry.getKey());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends CSSValue> Directive[] invokeParser(CSSOMNamedDeclarationParser<?> namedParser, TokenLike[] tokens) {
		CSSOMNamedDeclarationParser<T> castedParser = (CSSOMNamedDeclarationParser<T>) namedParser;
		Optional<T> result = getResult(tokens, castedParser.getPropertyValueParser());
		return result
			.map(value -> castedParser.translatePropertyValue(value))
			.orElse(EMPTY_DIRECTIVE_ARRAY);
	}

	private <T extends CSSValue> Optional<T> getResult(TokenLike[] tokens, PropertyValueParser<T> parser) {
		PropertyValueParseResult<T> result = parser.parse(tokens, 0, tokens.length);
		return result.getResult();
	}

}
