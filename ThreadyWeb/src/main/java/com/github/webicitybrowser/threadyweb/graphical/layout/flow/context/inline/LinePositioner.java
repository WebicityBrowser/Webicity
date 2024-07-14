package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective.TextAlign;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;

public final class LinePositioner {

	private LinePositioner() {}

	public static LineContainer positionLines(LineContainer lineContainer, FlowRenderContext context) {
		LinePositionContext linePositionContext = new LinePositionContext(
			context.layoutRenderContext(),
			new LineCursorTracker(lineContainer.direction()),
			context.flowRootContextSwitch());
		List<LineBox> positionedLines = new ArrayList<>();
		for (LineBox lineBox : lineContainer.lines()) {
			positionedLines.addAll(breakAndPositionLine(lineBox, linePositionContext));
		}

		return new LineContainer(lineContainer.direction(), positionedLines);
	}

	private static List<LineBox> breakAndPositionLine(LineBox lineBox, LinePositionContext linePositionContext) {
		List<LineBox> wrappedLines = new ArrayList<>();
		LineSplitter lineSplitter = new LineSplitter(lineBox);

		while (!lineSplitter.isDone()) {
			float availableInlineSize = determineAvailableInlineSize(linePositionContext);
			lineSplitter.splitFits(availableInlineSize);
			LineBox splitLineBox = lineSplitter.nextLine();

			Rectangle positioningInfo = determinePositionInfo(linePositionContext, splitLineBox);
			LineBox positionedLineBox = new LineBox(splitLineBox.direction(), splitLineBox.entries(), positioningInfo);
			wrappedLines.add(positionedLineBox);

			linePositionContext.lineCursorTracker().add(new AbsoluteSize(0, positioningInfo.size().height()));
			linePositionContext.lineCursorTracker().nextLine();
		}

		return wrappedLines;
	}

	private static float determineAvailableInlineSize(LinePositionContext linePositionContext) {
		FloatTracker floatTracker = linePositionContext.flowRootContextSwitch().floatContext().getFloatTracker();

		AbsolutePosition offsetPosition = getCursorAbsolutePosition(linePositionContext);
		float currentHeight = offsetPosition.y();
		
		// TODO: Vertical direction
		// TODO: What if width is unbounded?
		return linePositionContext.containerSize().width()
			- floatTracker.getLeftInlineOffset(currentHeight)
			- floatTracker.getRightInlineOffset(currentHeight, linePositionContext.containerSize().width());
	}

	private static Rectangle determinePositionInfo(LinePositionContext linePositionContext, LineBox lineBox) {
		TextAlign textAlign = getLineTextAlign(linePositionContext.styleDirectives());

		FloatTracker floatTracker = linePositionContext.flowRootContextSwitch().floatContext().getFloatTracker();
		AbsolutePosition offsetPosition = getCursorAbsolutePosition(linePositionContext);
		float currentHeight = offsetPosition.y();
		float containerWidth = linePositionContext.containerSize().width();

		float heightOverride = determineHeightOverride(linePositionContext);

		float leftFloat = floatTracker.getLeftInlineOffset(currentHeight);
		float rightFloat = floatTracker.getRightInlineOffset(currentHeight, linePositionContext.containerSize().width());

		float boxWidth = determineBoxWidth(lineBox);
		float boxHeight = determineBoxHeight(lineBox, linePositionContext);

		float availableWidth = containerWidth - leftFloat - rightFloat;
		float topPosition = offsetPosition.y();
		float leftOffset = leftFloat + offsetLine(boxWidth, availableWidth, textAlign);

		float actualHeight = heightOverride != RelativeDimension.UNBOUNDED ? heightOverride : boxHeight;

		return new Rectangle(
			new AbsolutePosition(leftOffset, topPosition),
			new AbsoluteSize(boxWidth, actualHeight));
	}

	private static boolean isEmptyBox(LineBox lineBox) {
		if (lineBox.entries().isEmpty()) return true;

		for (LineEntry entry : lineBox.entries()) {
			if (!(entry instanceof LineEntry.Text || entry instanceof LineEntry.PreserveLine)) return false;
			// TODO: Can we just use isBlank()?
			if (
				entry instanceof LineEntry.Text textEntry
				&& !textEntry.textUnit().text().replace(" ", "").isEmpty()
			) return false;
		}

		return true;
	}

	private static AbsolutePosition getCursorAbsolutePosition(LinePositionContext linePositionContext) {
		LineDimension offsetPosition = linePositionContext.lineCursorTracker().getNextPosition();
		return LineDimensionConverter.convertToAbsolutePosition(
			offsetPosition, linePositionContext.containerSize(), AbsoluteSize.ZERO_SIZE);
	}

	private static float determineBoxWidth(LineBox lineBox) {
		// TODO: Better
		float width = 0;
		for (LineEntry entry : lineBox.entries()) {
			width += entry.getSize().width();
		}

		return width;
	}

	private static float determineBoxHeight(LineBox lineBox, LinePositionContext linePositionContext) {
		if (isEmptyBox(lineBox)) {
			return WebFontUtil
				.getFont(
					linePositionContext.layoutRenderContext().componentUI(),
					linePositionContext.layoutRenderContext().globalRenderContext())
				.getMetrics()
				.getSize();
		};

		// TODO: Better
		float height = 0;
		for (LineEntry entry : lineBox.entries()) {
			height = Math.max(height, entry.getSize().height());
		}

		return height;
	}

	private static float offsetLine(float inlineWidth, float inlineAvailable, TextAlign textAlign) {
		if (inlineAvailable == RelativeDimension.UNBOUNDED) return 0;
		// TODO: Text direction, justify
		return switch (textAlign) {
			case START, LEFT -> 0;
			case CENTER -> (inlineAvailable - inlineWidth) / 2;
			case END, RIGHT -> inlineAvailable - inlineWidth;
			default -> 0;
		};
	}

	private static TextAlign getLineTextAlign(DirectivePool styleDirectives) {
		return styleDirectives
			.inheritDirectiveOrEmpty(TextAlignDirective.class)
			.map(TextAlignDirective::getTextAlign)
			.orElse(TextAlign.START);
	}

	private static float determineHeightOverride(LinePositionContext linePositionContext) {
		return FlowUtils.getLineHeight(linePositionContext.layoutRenderContext());
	}

	private static record LinePositionContext(
		LayoutRenderContext layoutRenderContext, LineCursorTracker lineCursorTracker, FlowRootContextSwitch flowRootContextSwitch
	) {
		public AbsoluteSize containerSize() {
			return layoutRenderContext.localRenderContext().preferredSize();
		}

		@Deprecated
		public DirectivePool styleDirectives() {
			return layoutRenderContext.layoutDirectives();
		}
	}

}
