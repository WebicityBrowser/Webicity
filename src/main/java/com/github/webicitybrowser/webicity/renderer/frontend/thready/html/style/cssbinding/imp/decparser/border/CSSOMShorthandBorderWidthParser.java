package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.width.BorderWidthShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.border.MultiBorderWidthValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.BottomBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.LeftBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.RightBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective.TopBorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMShorthandBorderWidthParser implements CSSOMNamedDeclarationParser<MultiBorderWidthValue> {

	private final BorderWidthShorthandValueParser shorthandParser = new BorderWidthShorthandValueParser();

	@Override
	public PropertyValueParser<MultiBorderWidthValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(MultiBorderWidthValue value) {
		return new Directive[] {
			BorderWidthDirective.ofLeft(getSizeCalculation(value.left())),
			BorderWidthDirective.ofRight(getSizeCalculation(value.right())),
			BorderWidthDirective.ofTop(getSizeCalculation(value.top())),
			BorderWidthDirective.ofBottom(getSizeCalculation(value.bottom()))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(
			LeftBorderWidthDirective.class, RightBorderWidthDirective.class,
			TopBorderWidthDirective.class, BottomBorderWidthDirective.class);
	}

	public static SizeCalculation getSizeCalculation(CSSValue value) {
		return SizeParser.parseNonPercent(value);
	}
	
}
