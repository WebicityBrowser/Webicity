package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

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
        AbsoluteSize outerSize = localRenderContext.getPreferredSize();
		RenderedUnit innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, outerSize);

		AbsoluteSize allowedSize = adjustForScrollbars(outerSize, outerSize, innerUnitRenderAttempt);
		innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, allowedSize);
		// Check again, because adding one scrollbar may have caused the other to be needed
		allowedSize = adjustForScrollbars(outerSize, allowedSize, innerUnitRenderAttempt);
		innerUnitRenderAttempt = renderInnerBox(box, globalRenderContext, localRenderContext, allowedSize);

		outerSize = removeUnboundedDimensions(outerSize, innerUnitRenderAttempt);
		
		return new ScrollUnit(box, outerSize, innerUnitRenderAttempt);
    }

	private static AbsoluteSize adjustForScrollbars(AbsoluteSize outerSize, AbsoluteSize allowedSize, RenderedUnit innerUnitRenderAttempt) {
		if (requiresVerticalScroll(outerSize, innerUnitRenderAttempt)) {
			allowedSize = new AbsoluteSize(outerSize.width() - ScrollBarStyles.TRACKBAR_INLINE_SIZE, allowedSize.height());
		}
		if (requiresHorizontalScroll(outerSize, innerUnitRenderAttempt)) {
			allowedSize = new AbsoluteSize(allowedSize.width(), outerSize.height() - ScrollBarStyles.TRACKBAR_INLINE_SIZE);
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

	private static RenderedUnit renderInnerBox(
		ScrollBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, AbsoluteSize allowedSize
	) {
		Box innerBox = box.innerBox();
		LocalRenderContext innerLocalRenderContext = LocalRenderContext.create(
			allowedSize,
			localRenderContext.getParentFontMetrics(),
			new ContextSwitch[0]);
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
