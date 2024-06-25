package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;

public final class FlexCrossSizeDetermination {
	
	private FlexCrossSizeDetermination() {}

	public static void determineLineCrossSizes(LayoutManagerContext layoutManagerContext, List<FlexLine> flexLines) {
		for (FlexLine flexLine: flexLines) {
			determineItemCrossSizes(layoutManagerContext, flexLine);
		}

		if (!(
			FlexUtils.getFlexWrap(layoutManagerContext.layoutDirectives()) == FlexWrap.NOWRAP &&
			determineNoWrapLineCrossSize(layoutManagerContext, flexLines.get(0))
		)) {
			for (FlexLine flexLine: flexLines) {
				determineFlexLineCrossSize(layoutManagerContext, flexLine);
			}
		}

		distributeExtraSpace(layoutManagerContext, flexLines);

		for (FlexLine flexLine: flexLines) {
			determineUsedItemCrossSizes(layoutManagerContext, flexLine);
		}
    }

	private static void distributeExtraSpace(LayoutManagerContext layoutManagerContext, List<FlexLine> flexLines) {
		float preferredCrossSize = FlexDimension
			.createFrom(
				layoutManagerContext.localRenderContext().preferredSize(),
				FlexUtils.getFlexDirection(layoutManagerContext.layoutDirectives()))
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

	private static boolean determineNoWrapLineCrossSize(LayoutManagerContext layoutManagerContext, FlexLine flexLine) {
		float parentSize = FlexDimension
			.createFrom(layoutManagerContext.localRenderContext().preferredSize(), flexLine.getFlexDirection())
			.cross();
		if (parentSize != RelativeDimension.UNBOUNDED) {
			flexLine.setCrossSize(parentSize);
			return true;
		}

		return false;
	}

	private static void determineFlexLineCrossSize(LayoutManagerContext layoutManagerContext, FlexLine flexLine) {
		float crossSize = flexLine.getFlexItems().stream()
			.map(FlexItem::getCrossSize)
			.max(Float::compare)
			.orElse(0f);
		flexLine.setCrossSize(crossSize);
	}

	public static void determineItemCrossSizes(LayoutManagerContext layoutManagerContext, FlexLine flexLine) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			determineInitialItemCrossSize(layoutManagerContext, flexItem, flexDirection);
		}
	}

	private static void determineUsedItemCrossSizes(LayoutManagerContext layoutManagerContext, FlexLine flexLine) {
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			determineUsedItemCrossSize(layoutManagerContext, flexLine, flexItem);
		}
	}

	private static void determineUsedItemCrossSize(LayoutManagerContext layoutManagerContext, FlexLine flexLine, FlexItem flexItem) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		flexItem.setCrossSize(flexLine.getCrossSize());
		GlobalRenderContext globalRenderContext = layoutManagerContext.globalRenderContext();
		FlexItemRenderer.FlexItemRenderContext flexItemRenderContext = new FlexItemRenderer.FlexItemRenderContext(
			globalRenderContext, flexDirection);
		// Make sure children of the item are rendered with the correct cross size
		FlexItemRenderer.render(flexItem, flexItemRenderContext);
	}

	private static void determineInitialItemCrossSize(LayoutManagerContext layoutManagerContext, FlexItem flexItem, FlexDirection flexDirection) {
		determineBaselineCrossSize(layoutManagerContext, flexItem, flexDirection);
	}

	private static void determineBaselineCrossSize(
		LayoutManagerContext layoutManagerContext, FlexItem flexItem, FlexDirection flexDirection
	) {
		GlobalRenderContext globalRenderContext = layoutManagerContext.globalRenderContext();
		FlexItemRenderer.FlexItemRenderContext flexItemRenderContext = new FlexItemRenderer.FlexItemRenderContext(
			globalRenderContext, flexDirection);
		flexItem.setCrossSize(flexItem.getSizePreferences().getCrossSize(flexDirection));
		AbsoluteSize fitSize = FlexItemRenderer.render(flexItem, flexItemRenderContext);
		FlexDimension flexDimension = FlexDimension.createFrom(fitSize, flexDirection);
		flexItem.setCrossSize(flexDimension.cross());
	}

}
