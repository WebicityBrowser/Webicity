package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.layout.flow;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.flow.LineHeightValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.NumberValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMLineHeightDeclarationParser implements CSSOMNamedDeclarationParser<CSSValue> {

	private final LineHeightValueParser lineHeightValueParser = new LineHeightValueParser();

	@Override
	public PropertyValueParser<CSSValue> getPropertyValueParser() {
		return lineHeightValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(CSSValue value) {
		if (value instanceof AutoValue) {
			return new Directive[] {
				LineHeightDirective.of(LineHeightDirective.NORMAL)
			};
		}

		SizeCalculation sizeCalculation = value instanceof NumberValue numberValue ?
			ctx -> ctx.relativeFont().getSize() * numberValue.getValue().floatValue() :
			SizeParser.parseWithFontPercents(value);
		return new Directive[] {
			LineHeightDirective.of(sizeCalculation)
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(LineHeightDirective.class);
	}

}
