package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;


import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;


public final class InlineTextRenderer {
	
	private InlineTextRenderer() {}

	public static TextUnit renderInitialText(TextBox textBox, LayoutRenderContext layoutRenderContext, float letterSpacing) {
		// The actual size will be calculated later
		return new TextUnit(
			new AbsoluteSize(0, 0), textBox, textBox.text(),
			textBox.getFont(layoutRenderContext.globalRenderContext(), layoutRenderContext.localRenderContext()),
			letterSpacing);
	}

	public static TextUnit renderNewText(TextUnit textUnit, String text) {
		Font2D font = textUnit.font();
		float letterSpacing = textUnit.letterSpacing();

		float textWidth = font.getMetrics().getStringWidth(text) + letterSpacing * (text.length() - 1);
		AbsoluteSize fitSize = new AbsoluteSize(
			textWidth,
			font.getMetrics().getCapHeight() + font.getMetrics().getDescent()
		);

		return new TextUnit(fitSize, textUnit.box(), text, font, letterSpacing);
	}

	public static TextUnit renderNewText(TextUnit textUnit) {
		return renderNewText(textUnit, textUnit.text());
	}

}
