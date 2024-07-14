package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public record TextBox(
	Context displayContext, String text, Font2D fontOverride
) implements Box {

	public TextBox(Context displayContext) {
		this(displayContext, ((TextComponent) displayContext.componentUI().getComponent()).getText(), null);
	}

	@Override
	public boolean isFluid() {
		return true;
	}

	public Font2D getFont(GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		if (fontOverride != null) return fontOverride;
		return WebFontUtil.getFont(componentUI(), renderContext);
	}
	
}
