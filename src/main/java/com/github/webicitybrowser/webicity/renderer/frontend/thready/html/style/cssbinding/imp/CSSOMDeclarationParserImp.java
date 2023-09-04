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
import com.github.webicitybrowser.threadyweb.graphical.directive.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMBackgroundColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMBoxSizingDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMColorDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMDisplayDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMFlexDirectionParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMFontFamilyParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMFontSizeDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMFontWeightDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMHeightDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMLonghandMarginParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMShorthandMarginParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMShorthandPaddingParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.CSSOMWidthDeclarationParser;

public class CSSOMDeclarationParserImp implements CSSOMDeclarationParser {
	
	private static final Logger logger = LoggerFactory.getLogger(CSSOMDeclarationParserImp.class);
	
	private final Map<String, CSSOMNamedDeclarationParser<?>> namedDeclarationParsers = new HashMap<>();
	
	public CSSOMDeclarationParserImp() {
		namedDeclarationParsers.put("color", new CSSOMColorDeclarationParser());
		namedDeclarationParsers.put("background-color", new CSSOMBackgroundColorDeclarationParser());
		namedDeclarationParsers.put("background", new CSSOMBackgroundColorDeclarationParser()); // TODO
		namedDeclarationParsers.put("display", new CSSOMDisplayDeclarationParser());
		namedDeclarationParsers.put("height", new CSSOMHeightDeclarationParser());
		namedDeclarationParsers.put("width", new CSSOMWidthDeclarationParser());
		namedDeclarationParsers.put("font-family", new CSSOMFontFamilyParser());
		namedDeclarationParsers.put("font-weight", new CSSOMFontWeightDeclarationParser());
		namedDeclarationParsers.put("font-size", new CSSOMFontSizeDeclarationParser());
		namedDeclarationParsers.put("margin-left", new CSSOMLonghandMarginParser(MarginDirective::ofLeft));
		namedDeclarationParsers.put("margin-right", new CSSOMLonghandMarginParser(MarginDirective::ofRight));
		namedDeclarationParsers.put("margin-top", new CSSOMLonghandMarginParser(MarginDirective::ofTop));
		namedDeclarationParsers.put("margin-bottom", new CSSOMLonghandMarginParser(MarginDirective::ofBottom));
		namedDeclarationParsers.put("margin", new CSSOMShorthandMarginParser());
		namedDeclarationParsers.put("padding-left", new CSSOMLonghandMarginParser(PaddingDirective::ofLeft));
		namedDeclarationParsers.put("padding-right", new CSSOMLonghandMarginParser(PaddingDirective::ofRight));
		namedDeclarationParsers.put("padding-top", new CSSOMLonghandMarginParser(PaddingDirective::ofTop));
		namedDeclarationParsers.put("padding-bottom", new CSSOMLonghandMarginParser(PaddingDirective::ofBottom));
		namedDeclarationParsers.put("padding", new CSSOMShorthandPaddingParser());
		namedDeclarationParsers.put("box-sizing", new CSSOMBoxSizingDeclarationParser());
		namedDeclarationParsers.put("flex-direction", new CSSOMFlexDirectionParser());
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
