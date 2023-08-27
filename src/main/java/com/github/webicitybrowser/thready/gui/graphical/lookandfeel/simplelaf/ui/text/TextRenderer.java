package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public final class TextRenderer {

	public static TextUnit createTextUnit(TextBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		FontSettings fontSettings = getFontSettings(box);
		Font2D font = renderContext.getResourceLoader().loadFont(fontSettings);
		String text = box.owningComponent().getText();

		float width = font.getMetrics().getStringWidth(text);
		float height =
			font.getMetrics().getCapHeight() +
			font.getMetrics().getDescent() +
			font.getMetrics().getLeading();
		AbsoluteSize preferredSize = new AbsoluteSize(width, height);

		return new TextUnit(preferredSize, box, text, font);
	}

	private static FontSettings getFontSettings(TextBox box) {
		return SimpleDirectiveUtil.getFontSettings(box.styleDirectives());
	}

}
