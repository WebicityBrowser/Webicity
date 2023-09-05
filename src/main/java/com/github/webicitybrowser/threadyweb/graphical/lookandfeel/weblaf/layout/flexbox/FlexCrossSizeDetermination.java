package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.item.FlexItem;

public final class FlexCrossSizeDetermination {
	
	private FlexCrossSizeDetermination() {}

	public static void determineLineCrossSizes(
		Box box, List<FlexLine> flexLines, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		for (FlexLine flexLine: flexLines) {
			determineItemCrossSizes(flexLine, globalRenderContext, localRenderContext);
		}
		if (!(FlexUtils.getFlexWrap(box) == FlexWrap.NOWRAP && determineNoWrapLineCrossSize(flexLines.get(0), localRenderContext))) {
			for (FlexLine flexLine: flexLines) {
				determineFlexLineCrossSize(flexLine, globalRenderContext, localRenderContext);
			}
		}

		distributeExtraSpace(box, flexLines, localRenderContext);

		for (FlexLine flexLine: flexLines) {
			determineUsedItemCrossSizes(flexLine, globalRenderContext, localRenderContext);
		}
    }

	private static void distributeExtraSpace(Box box, List<FlexLine> flexLines, LocalRenderContext localRenderContext) {
		float preferredCrossSize = FlexDimension
			.createFrom(localRenderContext.getPreferredSize(), FlexUtils.getFlexDirection(box))
			.cross();
		if (preferredCrossSize == RelativeDimension.UNBOUNDED) return;
		float totalCrossSize = flexLines.stream()
			.map(FlexLine::getCrossSize)
			.reduce(0f, Float::sum);
		float extraSpace = preferredCrossSize - totalCrossSize;
		if (extraSpace <= 0) return;
		float extraSpacePerLine = extraSpace / flexLines.size();
		for (FlexLine flexLine: flexLines) {
			flexLine.setCrossSize(flexLine.getCrossSize() + extraSpacePerLine);
		}
	}

	private static boolean determineNoWrapLineCrossSize(FlexLine flexLine, LocalRenderContext localRenderContext) {
		float parentSize = FlexDimension
			.createFrom(localRenderContext.getPreferredSize(), flexLine.getFlexDirection())
			.cross();
		if (parentSize != RelativeDimension.UNBOUNDED) {
			flexLine.setCrossSize(parentSize);
			return true;
		}

		return false;
	}

	private static void determineFlexLineCrossSize(
		FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		float crossSize = flexLine.getFlexItems().stream()
			.map(FlexItem::getCrossSize)
			.max(Float::compare)
			.orElse(0f);
		flexLine.setCrossSize(crossSize);
	}

	public static void determineItemCrossSizes(FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			determineInitialItemCrossSize(flexItem, flexDirection, globalRenderContext, localRenderContext);
		}
	}

	private static void determineUsedItemCrossSizes(
		FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			determineUsedItemCrossSize(flexLine, flexItem, globalRenderContext, localRenderContext);
		}
	}

	private static void determineUsedItemCrossSize(
		FlexLine flexLine, FlexItem flexItem, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		flexItem.setCrossSize(flexLine.getCrossSize());
		FlexItemRenderer.FlexItemRenderContext flexItemRenderContext = new FlexItemRenderer.FlexItemRenderContext(
			globalRenderContext, flexDirection, localRenderContext.getParentFontMetrics()
		);
		// Make sure children of the item are rendered with the correct cross size
		FlexItemRenderer.render(flexItem, flexItemRenderContext);
	}

	private static void determineInitialItemCrossSize(
		FlexItem flexItem, FlexDirection flexDirection, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		determineBaselineCrossSize(flexItem, flexDirection, globalRenderContext, localRenderContext);
	}

	private static void determineBaselineCrossSize(
		FlexItem flexItem, FlexDirection flexDirection, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		FlexItemRenderer.FlexItemRenderContext flexItemRenderContext = new FlexItemRenderer.FlexItemRenderContext(
			globalRenderContext, flexDirection, localRenderContext.getParentFontMetrics()
		);
		AbsoluteSize fitSize = FlexItemRenderer.render(flexItem, flexItemRenderContext);
		FlexDimension flexDimension = FlexDimension.createFrom(fitSize, flexDirection);
		flexItem.setCrossSize(flexDimension.cross());
	}

}
