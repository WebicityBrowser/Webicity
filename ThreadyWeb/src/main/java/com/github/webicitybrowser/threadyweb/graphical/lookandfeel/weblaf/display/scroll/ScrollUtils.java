package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;

public final class ScrollUtils {
	
	private ScrollUtils() {}

	public static Rectangle getScrollbarLocation(ScrollUnit unit, Rectangle documentRect, ScrollbarDimensionTranslator positionTranslator) {
		AbsolutePosition translatedDocumentPosition = positionTranslator.translateToUpright(documentRect.position());
		AbsoluteSize translatedDocumentSize = positionTranslator.translateToUpright(documentRect.size());
		float availableBlockSize = translatedDocumentSize.height();
		float pageBlockSize = positionTranslator.translateToUpright(unit.innerUnit().fitSize()).height();
		float scrollThrough = positionTranslator.translateToUpright(unit.box().scrollContext().scrollPosition()).y();

		if (scrollThrough == ScrollbarStyles.NOT_PRESENT) {
			return new Rectangle(
				new AbsolutePosition(ScrollbarStyles.NOT_PRESENT, ScrollbarStyles.NOT_PRESENT),
				new AbsoluteSize(ScrollbarStyles.NOT_PRESENT, ScrollbarStyles.NOT_PRESENT));
		}

		float scrollBlockSize = determineScrollbarSize(availableBlockSize, pageBlockSize);
		float scrollBlockPosition = translatedDocumentPosition.y() + determineScrollbarPosition(availableBlockSize, pageBlockSize, scrollThrough);
		
		float scrollbarInlineSize = ScrollbarStyles.SCROLLBAR_INLINE_SIZE;
		float scrollbarInlinePosition = 
			translatedDocumentPosition.x() + documentRect.size().width() -
			ScrollbarStyles.PADDING_INLINE_SIZE - ScrollbarStyles.SCROLLBAR_INLINE_SIZE;

		return new Rectangle(
			positionTranslator.translateFromUpright(new AbsolutePosition(scrollbarInlinePosition, scrollBlockPosition)),
			positionTranslator.translateFromUpright(new AbsoluteSize(scrollbarInlineSize, scrollBlockSize)));
	}

	public static float computePageScrollChange(
		ScrollUnit unit, Rectangle documentRect, float scrollBarMovement, ScrollbarDimensionTranslator positionTranslator
	) {
		AbsoluteSize translatedDocumentSize = positionTranslator.translateToUpright(documentRect.size());
		float availableBlockSize = translatedDocumentSize.height();
		float pageBlockSize = positionTranslator.translateToUpright(unit.innerUnit().fitSize()).height();

		float actualAvailableBlockSize = availableBlockSize - ScrollbarStyles.PADDING_BLOCK_SIZE * 2;
		float scaledScrollBlockMovement = scrollBarMovement / actualAvailableBlockSize * pageBlockSize;

		return scaledScrollBlockMovement;
	}

	private static float determineScrollbarSize(float availableBlockSize, float pageBlockSize) {
		float actualAvailableBlockSize = availableBlockSize - ScrollbarStyles.PADDING_BLOCK_SIZE * 2;
		float scrollbarSize = availableBlockSize / pageBlockSize * actualAvailableBlockSize;
		scrollbarSize = Math.max(scrollbarSize, ScrollbarStyles.MINIMUM_SCROLLBAR_BLOCK_SIZE);

		return scrollbarSize;
	}

	private static float determineScrollbarPosition(float availableBlockSize, float pageBlockSize, float scrollThrough) {
		float actualAvailableBlockSize = availableBlockSize - ScrollbarStyles.PADDING_BLOCK_SIZE * 2;
		float scrollbarPosition = actualAvailableBlockSize / pageBlockSize * scrollThrough;

		return scrollbarPosition + ScrollbarStyles.PADDING_BLOCK_SIZE;
	}

}
