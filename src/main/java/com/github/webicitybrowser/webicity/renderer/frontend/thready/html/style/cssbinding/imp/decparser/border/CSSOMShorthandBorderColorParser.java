package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.color.BorderColorShorthandValueParser;
import com.github.webicitybrowser.spec.css.property.border.MultiBorderColorValue;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.BottomBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.LeftBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.RightBorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.TopBorderColorDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.ColorParser;

public class CSSOMShorthandBorderColorParser implements CSSOMNamedDeclarationParser<MultiBorderColorValue> {

	private final BorderColorShorthandValueParser shorthandParser = new BorderColorShorthandValueParser();

	@Override
	public PropertyValueParser<MultiBorderColorValue> getPropertyValueParser() {
		return shorthandParser;
	}

	@Override
	public Directive[] translatePropertyValue(MultiBorderColorValue value) {
		return new Directive[] {
			BorderColorDirective.ofLeft(getColor(value.left())),
			BorderColorDirective.ofRight(getColor(value.right())),
			BorderColorDirective.ofTop(getColor(value.top())),
			BorderColorDirective.ofBottom(getColor(value.bottom()))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(
			LeftBorderColorDirective.class, RightBorderColorDirective.class,
			TopBorderColorDirective.class, BottomBorderColorDirective.class);
	}

	public static ColorFormat getColor(ColorValue value) {
		return ColorParser.parseColor(value);
	}
	
}
