package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;

public final class ScrollbarStyles {
	
	public static final float PADDING_BLOCK_SIZE = 8;
	public static final float PADDING_INLINE_SIZE = 2;
	public static final float SCROLLBAR_INLINE_SIZE = 8;
	public static final float TRACKBAR_INLINE_SIZE = SCROLLBAR_INLINE_SIZE + PADDING_INLINE_SIZE * 2;
	public static final float MINIMUM_SCROLLBAR_BLOCK_SIZE = 16;

	// TODO: Do not hardcode colors
	public static final ColorFormat SCROLLBAR_COLOR = new RGBA8Color(0, 178, 152);

	public static final float NOT_PRESENT = -1;
	public static final ScrollbarDimensionTranslator VERTICAL_SCROLLBAR = new VerticalScrollbarDimensionTranslator();
	public static final ScrollbarDimensionTranslator HORIZONTAL_SCROLLBAR = new HorizontalScrollbarDimensionTranslator();

	private ScrollbarStyles() {}

}
