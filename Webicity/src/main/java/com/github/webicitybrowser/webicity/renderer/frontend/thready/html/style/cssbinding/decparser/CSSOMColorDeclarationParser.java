package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.color.ColorValueParser;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.directive.ForegroundColorDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.componentparser.ColorParser;

public class CSSOMColorDeclarationParser implements CSSOMNamedDeclarationParser<ColorValue> {

	private final PropertyValueParser<ColorValue> colorParser = new ColorValueParser();
	
	@Override
	public PropertyValueParser<ColorValue> getPropertyValueParser() {
		return this.colorParser;
	}

	@Override
	public Directive[] translatePropertyValue(ColorValue value) {
		return new Directive[] {
			ForegroundColorDirective.of(ColorParser.parseColor(value))
		};
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(ForegroundColorDirective.class);
	}

}
