package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import java.util.Optional;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.source.FontSource;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.derived.DerivedFontDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontFamilyDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontSizeDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.FontWeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class WebFontUtil {

	private WebFontUtil() {}

	public static Font2D getFont(DirectivePool styleDirectives, GlobalRenderContext globalRenderContext) {
		return styleDirectives.inheritDirectiveOrEmpty(DerivedFontDirective.class).orElseThrow().getFont();
	}

	public static DerivedFontDirective deriveFont(DirectivePool self, DirectivePool parent, StyleContext styleContext) {
		DerivedFontDirective parentFontDirective = parent == null ? null : parent.getDirectiveOrEmpty(DerivedFontDirective.class).orElse(null);

		Optional<FontFamilyDirective> fontFamilyDirective = self.getDirectiveOrEmpty(FontFamilyDirective.class);
		Optional<FontSizeDirective> fontSizeDirective = self.getDirectiveOrEmpty(FontSizeDirective.class);
		Optional<FontWeightDirective> fontWeightDirective = self.getDirectiveOrEmpty(FontWeightDirective.class);

		if (parentFontDirective != null && fontFamilyDirective.isEmpty() && fontSizeDirective.isEmpty() && fontWeightDirective.isEmpty()) {
			return parentFontDirective;
		}
		
		FontMetrics parentMetrics = parentFontDirective != null ?
			parentFontDirective.getFont().getMetrics() :
			styleContext.rootFontMetrics();
		
		FontSource[] parentSource = parentFontDirective != null ?
			parentFontDirective.getFont().getSettings().fontSources() :
			WebDefaults.FONT.fontSources();
		FontSource[] fontSources = getFontSources(fontFamilyDirective, parentSource);
		float fontSize = getFontSize(fontSizeDirective, new SizeCalculationContext(
			null, styleContext.viewportSize(),
			parentMetrics, styleContext.rootFontMetrics()));
		int fontWeight = getFontWeight(fontWeightDirective, parentMetrics);

		return DerivedFontDirective.of(styleContext.resourceLoader().loadFont(
			new FontSettings(fontSources, fontSize, fontWeight, new FontDecoration[0])
		));
	}

	private static FontSource[] getFontSources(Optional<FontFamilyDirective> fontFamilyDirective, FontSource[] parentSource) {
		return fontFamilyDirective
			.map(directive -> directive.getFontFamilies())
			.map(fontFamilies -> fontFamilies)
			.orElse(parentSource);
	}

	public static float getFontSize(Optional<FontSizeDirective> fontSizeDirective, SizeCalculationContext context) {
		return fontSizeDirective
			.map(directive -> directive.getSizeCalculation())
			.map(calculation -> calculation.calculate(context, true))
			.orElse(context.relativeFont().getSize());
	}

	public static int getFontWeight(Optional<FontWeightDirective> fontWeightDirective, FontMetrics parentMetrics) {
		return fontWeightDirective
			.map(directive -> directive.getFontWeight())
			.map(weight -> weight.getWeight(parentMetrics.getWeight()))
			.orElse(parentMetrics.getWeight());
	}

}
