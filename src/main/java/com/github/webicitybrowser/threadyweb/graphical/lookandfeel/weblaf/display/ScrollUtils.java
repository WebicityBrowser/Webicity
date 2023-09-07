package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;

public final class ScrollUtils {
	
	private ScrollUtils() {}

	public static Rectangle getVerticalScrollbarLocation(ScrollUnit unit, Rectangle documentRect) {
		float availableBlockSize = documentRect.size().height();
		float pageBlockSize = unit.innerUnit().fitSize().height();
		float scrollThrough = unit.box().scrollContext().scrollPosition().y();

		float scrollBlockSize = determineScrollbarSize(availableBlockSize, pageBlockSize);
		float scrollBlockPosition = determineScrollbarPosition(availableBlockSize, pageBlockSize, scrollThrough);
		
		float scrollbarInlineSize = ScrollBarStyles.SCROLLBAR_INLINE_SIZE;
		float scrollbarInlinePosition = documentRect.size().width() - ScrollBarStyles.PADDING_INLINE_SIZE - ScrollBarStyles.SCROLLBAR_INLINE_SIZE;

		return new Rectangle(
			new AbsolutePosition(scrollbarInlinePosition, scrollBlockPosition),
			new AbsoluteSize(scrollbarInlineSize, scrollBlockSize));
	}

	private static float determineScrollbarSize(float availableBlockSize, float pageBlockSize) {
		float actualAvailableBlockSize = availableBlockSize - ScrollBarStyles.PADDING_BLOCK_SIZE * 2;
		float scrollbarSize = pageBlockSize / actualAvailableBlockSize;
		scrollbarSize = Math.max(scrollbarSize, ScrollBarStyles.MINIMUM_SCROLLBAR_BLOCK_SIZE);

		return scrollbarSize;
	}

	private static float determineScrollbarPosition(float availableBlockSize, float pageBlockSize, float scrollThrough) {
		float actualAvailableBlockSize = availableBlockSize - ScrollBarStyles.PADDING_BLOCK_SIZE * 2;
		float scrollbarPosition = actualAvailableBlockSize / pageBlockSize * scrollThrough;

		return scrollbarPosition + ScrollBarStyles.PADDING_BLOCK_SIZE;
	}

}
