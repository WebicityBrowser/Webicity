package everyos.browser.webicity.threadygui.renderer.html.cssom.declaration;

import everyos.desktop.thready.basic.directive.ForegroundColorDirective;
import everyos.desktop.thready.core.graphics.color.colors.RGBA8Color;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.property.PropertyValueParseResult;
import everyos.web.spec.css.parser.property.color.ColorPropertyValueParser;
import everyos.web.spec.css.property.color.ColorValue;

public final class ColorDeclarationParser {

	private ColorDeclarationParser() {}
	
	public static Directive parse(TokenLike[] tokens) {
		PropertyValueParseResult<ColorValue> colorResult = new ColorPropertyValueParser()
			.parse(tokens, 0, tokens.length);
		if (colorResult.getLength() != tokens.length || colorResult.getResult().isEmpty()) {
			return null;
		}
		ColorValue color = colorResult.getResult().get();
		
		ColorFormat formattedColor = new RGBA8Color(
			color.getRed(),
			color.getGreen(),
			color.getBlue(),
			color.getAlpha()
		);
		return ForegroundColorDirective.of(formattedColor);
	}
	
}
