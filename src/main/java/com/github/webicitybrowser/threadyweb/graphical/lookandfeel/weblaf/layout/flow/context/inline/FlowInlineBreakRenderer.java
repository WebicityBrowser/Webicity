package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;

/**
 * This class allows for handling an explicit break (<br>) in an
 * inline flow context. It ensures the line will be at least as
 * large as the font height, and then creates a new line.
 */
public class FlowInlineBreakRenderer {

	private FlowInlineBreakRenderer() {}

	/**
	 * Adds an explicit break to the line.
	 * @param state The inline context state
	 */
	public static void addBreakBoxToLine(FlowInlineRendererState state) {
		Font2D font = state.getFontStack().peek();
		float fontHeight = font.getMetrics().getCapHeight() + font.getMetrics().getDescent();

		state.lineContext().currentLine().ensureMinHeight(fontHeight);
		FlowInlineRendererUtil.startNewLine(state);
	}

}
