package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public final class WebFontUtil {

	private WebFontUtil() {}

	public static Font2D getFont(DirectivePool styleDirectives, LocalRenderContext localRenderContext, GlobalRenderContext globalRenderContext) {
		// TODO: localRenderContext.getParentFont()
		FontSettings fontSettings = new FontSettings(
			WebDirectiveUtil.getFontSource(styleDirectives),
			WebDirectiveUtil.getFontSize(styleDirectives),
			WebDirectiveUtil.getFontWeight(styleDirectives).getWeight(400),
			new FontDecoration[0]);

		return globalRenderContext.getResourceLoader().loadFont(fontSettings);
	}

}
