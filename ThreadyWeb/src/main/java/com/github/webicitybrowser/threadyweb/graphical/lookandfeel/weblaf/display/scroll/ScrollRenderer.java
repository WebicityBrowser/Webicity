package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public final class ScrollRenderer {

	private ScrollRenderer() {}

	public static ScrollUnit renderBox(ScrollBox box, GlobalRenderContext globalRenderContext,  LocalRenderContext localRenderContext) {
		AbsoluteSize outerSize = localRenderContext.preferredSize();
		RenderedUnit innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, outerSize);
		AbsoluteSize initialInnerUnitSize = innerUnitRenderAttempt.fitSize();

		AbsoluteSize allowedSize = adjustForScrollbars(outerSize, outerSize, innerUnitRenderAttempt);
		innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, allowedSize);
		// Check again, because adding one scrollbar may have caused the other to be needed
		allowedSize = adjustForScrollbars(outerSize, allowedSize, innerUnitRenderAttempt);
		innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, allowedSize);

		outerSize = removeUnboundedDimensions(outerSize, innerUnitRenderAttempt);
		setUnpresentScrollPosition(box, initialInnerUnitSize, outerSize);
		
		return new ScrollUnit(box, outerSize, innerUnitRenderAttempt, allowedSize);
	}

	private static AbsoluteSize adjustForScrollbars(AbsoluteSize outerSize, AbsoluteSize allowedSize, RenderedUnit innerUnitRenderAttempt) {
		if (requiresVerticalScroll(outerSize, innerUnitRenderAttempt)) {
			allowedSize = new AbsoluteSize(outerSize.width() - ScrollbarStyles.TRACKBAR_INLINE_SIZE, allowedSize.height());
		}
		if (requiresHorizontalScroll(outerSize, innerUnitRenderAttempt)) {
			allowedSize = new AbsoluteSize(allowedSize.width(), outerSize.height() - ScrollbarStyles.TRACKBAR_INLINE_SIZE);
		}

		return allowedSize;
	}

	private static AbsoluteSize removeUnboundedDimensions(AbsoluteSize outerSize, RenderedUnit innerUnitRenderAttempt) {
		if (outerSize.width() == RelativeDimension.UNBOUNDED) {
			outerSize = new AbsoluteSize(innerUnitRenderAttempt.fitSize().width(), outerSize.height());
		}
		if (outerSize.height() == RelativeDimension.UNBOUNDED) {
			outerSize = new AbsoluteSize(outerSize.width(), innerUnitRenderAttempt.fitSize().height());
		}

		return outerSize;
	}

	private static void setUnpresentScrollPosition(ScrollBox box, AbsoluteSize contentSize, AbsoluteSize outerSize) {
		AbsolutePosition scrollPosition = box.scrollContext().scrollPosition();
		if (contentSize.width() <= outerSize.width()) {
			scrollPosition = new AbsolutePosition(ScrollbarStyles.NOT_PRESENT, scrollPosition.y());
		} else if (scrollPosition.x() == ScrollbarStyles.NOT_PRESENT) {
			scrollPosition = new AbsolutePosition(0, scrollPosition.y());
		}

		if (contentSize.height() <= outerSize.height()) {
			scrollPosition = new AbsolutePosition(scrollPosition.x(), ScrollbarStyles.NOT_PRESENT);
		} else if (scrollPosition.y() == ScrollbarStyles.NOT_PRESENT) {
			scrollPosition = new AbsolutePosition(scrollPosition.x(), 0);
		}

		box.scrollContext().setScrollPosition(scrollPosition);
	}

	private static RenderedUnit renderInnerBox(
		ScrollBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, AbsoluteSize allowedSize
	) {
		Box innerBox = box.innerBox();
		LocalRenderContext innerLocalRenderContext = LocalRenderContext.create(allowedSize, new ContextSwitch[0]);
		RenderedUnit innerUnitRenderAttempt = UIPipeline.render(innerBox, globalRenderContext, innerLocalRenderContext);

		return innerUnitRenderAttempt;
	}

	private static boolean requiresVerticalScroll(AbsoluteSize outerSize, RenderedUnit innerUnitRenderAttempt) {
		return
			innerUnitRenderAttempt.fitSize().height() != RelativeDimension.UNBOUNDED &&
			innerUnitRenderAttempt.fitSize().height() > outerSize.height();
	}

	private static boolean requiresHorizontalScroll(AbsoluteSize outerSize, RenderedUnit innerUnitRenderAttempt) {
		return
			innerUnitRenderAttempt.fitSize().width() != RelativeDimension.UNBOUNDED &&
			innerUnitRenderAttempt.fitSize().width() > outerSize.width();
	}

}
