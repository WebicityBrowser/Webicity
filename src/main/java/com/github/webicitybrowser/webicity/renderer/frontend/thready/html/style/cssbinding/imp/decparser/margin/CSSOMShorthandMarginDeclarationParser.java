package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.margin;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.margin.MarginShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.margin.MarginValue;
import com.github.webicitybrowser.spec.css.property.shared.basic.AutoValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.BottomMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.LeftMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.RightMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective.TopMarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMShorthandMarginDeclarationParser implements CSSOMNamedDeclarationParser<MarginValue> {

	private final MarginShorthandValueParser shorthandParser = new MarginShorthandValueParser();

	@Override
	public PropertyValueParser<MarginValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(MarginValue value) {
		return new Directive[] {
			MarginDirective.ofLeft(getSizeCalculation(value.left())),
			MarginDirective.ofRight(getSizeCalculation(value.right())),
			MarginDirective.ofTop(getSizeCalculation(value.top())),
			MarginDirective.ofBottom(getSizeCalculation(value.bottom()))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(
			LeftMarginDirective.class, RightMarginDirective.class,
			TopMarginDirective.class, BottomMarginDirective.class);
	}

	public static SizeCalculation getSizeCalculation(CSSValue value) {
		return value instanceof AutoValue ?
			SizeCalculation.SIZE_AUTO :
			SizeParser.parseWithBoxPercents(value);
	}
	
}
