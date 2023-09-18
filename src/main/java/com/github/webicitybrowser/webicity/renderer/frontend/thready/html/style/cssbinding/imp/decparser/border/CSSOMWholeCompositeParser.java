package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.border;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.border.composite.BorderCompositeValueParser;
import com.github.webicitybrowser.spec.css.property.border.BorderCompositeValue;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.ColorParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.componentparser.SizeParser;

public class CSSOMWholeCompositeParser implements CSSOMNamedDeclarationParser<BorderCompositeValue> {

	private final PropertyValueParser<BorderCompositeValue> longhandBorderColorValueParser = new BorderCompositeValueParser();

	@Override
	public PropertyValueParser<BorderCompositeValue> getPropertyValueParser() {
		return longhandBorderColorValueParser;
	}

	@Override
	public Directive[] translatePropertyValue(BorderCompositeValue value) {
		List<Directive> directives = new ArrayList<>();
		
		if (value.color() != null) {
			ColorFormat color = ColorParser.parseColor(value.color());
			directives.add(BorderColorDirective.ofLeft(color));
			directives.add(BorderColorDirective.ofRight(color));
			directives.add(BorderColorDirective.ofTop(color));
			directives.add(BorderColorDirective.ofBottom(color));
		}
		if (value.width() != null) {
			SizeCalculation width = SizeParser.parseNonPercent(value.width());
			directives.add(BorderWidthDirective.ofLeft(width));
			directives.add(BorderWidthDirective.ofRight(width));
			directives.add(BorderWidthDirective.ofTop(width));
			directives.add(BorderWidthDirective.ofBottom(width));
		}

		return directives.toArray(Directive[]::new);
	}
	
}
