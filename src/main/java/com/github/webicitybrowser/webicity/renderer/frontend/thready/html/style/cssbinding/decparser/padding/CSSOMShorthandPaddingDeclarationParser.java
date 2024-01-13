package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.padding;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.padding.PaddingShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.padding.PaddingValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.BottomPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.LeftPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.RightPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective.TopPaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.SizeParser;

public class CSSOMShorthandPaddingDeclarationParser implements CSSOMNamedDeclarationParser<PaddingValue> {

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

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(
			LeftPaddingDirective.class, RightPaddingDirective.class,
			TopPaddingDirective.class, BottomPaddingDirective.class);
	}
	
}
