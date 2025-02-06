package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.decparser.background;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.background.BackgroundValueParser;
import com.github.webicitybrowser.spec.css.property.background.BackgroundValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundValue.BackgroundLayer;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;
import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMNamedDeclarationParser;

public class CSSOMBackgroundDeclarationParser implements CSSOMNamedDeclarationParser<BackgroundValue> {

	private final PropertyValueParser<BackgroundValue> backgroundParser = new BackgroundValueParser();

	@Override
	public PropertyValueParser<BackgroundValue> getPropertyValueParser() {
		return this.backgroundParser;
	}

	@Override
	public Directive[] translatePropertyValue(BackgroundValue value) {
		List<Directive> directives = new ArrayList<>();

		BackgroundLayer lastLayer = value.layer().get(value.layer().size() - 1);

		if (lastLayer.color() != null) {
			directives.add(BackgroundColorDirective.of(createColorFrom(lastLayer.color())));
		} else {
			directives.add(BackgroundColorDirective.of(Colors.TRANSPARENT));
		}

		return directives.toArray(Directive[]::new);
	}

	@Override
	public List<Class<? extends Directive>> getResultantDirectiveClasses() {
		return List.of(BackgroundColorDirective.class);
	}

	private ColorFormat createColorFrom(ColorValue value) {
		return new RGBA8Color(value.red(), value.green(), value.blue(), value.alpha());
	}
	
}
