package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public final class TextRenderer {

	public static TextUnit createTextUnit(TextBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		Font2D font = box.getFont(globalRenderContext, localRenderContext);
		String text = box.text();

		float width = font.getMetrics().getStringWidth(text);
		float height = font.getMetrics().getCapHeight();
		AbsoluteSize fitSize = new AbsoluteSize(width, height);

		return new TextUnit(fitSize, box, text, font, 0);
	}

}
