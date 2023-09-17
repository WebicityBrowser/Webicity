package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.color.ColorValueParser;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMNamedDeclarationParser;

public class CSSOMBackgroundColorDeclarationParser implements CSSOMNamedDeclarationParser<ColorValue> {

	private final PropertyValueParser<ColorValue> colorParser = new ColorValueParser();
	
	@Override
	public PropertyValueParser<ColorValue> getPropertyValueParser() {
		return this.colorParser;
	}

	@Override
	public Directive[] translatePropertyValue(ColorValue value) {
		return new Directive[] {
			BackgroundColorDirective.of(createColorFrom(value))
		};
	}
	
	private ColorFormat createColorFrom(ColorValue value) {
		return new RGBA8Color(value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha());
	}

}
