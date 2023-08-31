package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebTextDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class WebFontUtil {

	private WebFontUtil() {}

	public static Font2D getFont(DirectivePool styleDirectives, SizeCalculationContext sizeContext, GlobalRenderContext globalRenderContext) {
		FontSettings fontSettings = new FontSettings(
			WebTextDirectiveUtil.getFontSource(styleDirectives),
			WebTextDirectiveUtil.getFontSize(styleDirectives, sizeContext),
			WebTextDirectiveUtil.getFontWeight(styleDirectives, sizeContext.relativeFont()),
			new FontDecoration[0]);

		return globalRenderContext.resourceLoader().loadFont(fontSettings);
	}

}
