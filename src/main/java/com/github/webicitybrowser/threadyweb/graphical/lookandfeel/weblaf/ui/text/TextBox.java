package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public record TextBox(
	UIDisplay<?, ?, ?> display, TextComponent owningComponent, DirectivePool styleDirectives, String text, Font2D fontOverride
) implements Box {

	public TextBox(UIDisplay<?, ?, ?> display, TextComponent owningComponent, DirectivePool styleDirectives) {
		this(display, owningComponent, styleDirectives, owningComponent.getText(), null);
	}

	@Override
	public boolean isFluid() {
		return true;
	}

	public Font2D getFont(GlobalRenderContext renderContext) {
		if (fontOverride != null) {
			return fontOverride;
		}

		FontSettings fontSettings = getFontSettings();
		return renderContext.getResourceLoader().loadFont(fontSettings);
	}
	
	private FontSettings getFontSettings() {
		return new FontSettings(
			WebDirectiveUtil.getFontSource(styleDirectives),
			WebDirectiveUtil.getFontSize(styleDirectives),
			WebDirectiveUtil.getFontWeight(styleDirectives).getWeight(400),
			new FontDecoration[0]);
	}
	
}
