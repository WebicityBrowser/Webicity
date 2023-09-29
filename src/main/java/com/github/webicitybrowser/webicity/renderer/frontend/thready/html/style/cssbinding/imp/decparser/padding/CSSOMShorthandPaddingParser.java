package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.padding;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.padding.PaddingShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.padding.PaddingValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMShorthandPaddingParser implements CSSOMNamedDeclarationParser<PaddingValue> {

	private final PaddingShorthandValueParser shorthandParser = new PaddingShorthandValueParser();

	@Override
	public PropertyValueParser<PaddingValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(PaddingValue value) {
		return new Directive[] {
			PaddingDirective.ofLeft(getSizeCalculation(value.left())),
			PaddingDirective.ofRight(getSizeCalculation(value.right())),
			PaddingDirective.ofTop(getSizeCalculation(value.top())),
			PaddingDirective.ofBottom(getSizeCalculation(value.bottom()))
		};
	}

	public static SizeCalculation getSizeCalculation(CSSValue value) {
		return value instanceof AutoValue ?
			SizeCalculation.SIZE_AUTO :
			SizeParser.parseWithBoxPercents(value);
	}
	
}
