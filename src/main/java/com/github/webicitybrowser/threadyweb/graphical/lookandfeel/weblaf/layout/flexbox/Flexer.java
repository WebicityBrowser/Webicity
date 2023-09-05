package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public final class Flexer {
	
	private Flexer() {}

	public static void resolveFlexibleLengths(FlexLine flexLine, LocalRenderContext localRenderContext) {
		float lineMainSize = FlexDimension
			.createFrom(localRenderContext.getPreferredSize(), flexLine.getFlexDirection())
			.main();

		List<FlexItem> flexItems = flexLine.getFlexItems();
		if (lineMainSize == RelativeDimension.UNBOUNDED) {
			// TODO: Handle infinite sizes properly
			freezeAllItems(flexItems);
			return;
		}

		float hypotheticalMainSize = sumHypotheticalMainSizes(flexItems);
		boolean isFlexShrink = hypotheticalMainSize > lineMainSize;

		freezeInflexibleItems(flexItems, hypotheticalMainSize, isFlexShrink);
		while (!flexItems.stream().allMatch(FlexItem::isFrozen)) {
			peformFlex(flexItems, lineMainSize, isFlexShrink);
		}
	}

	private static void peformFlex(List<FlexItem> flexItems, float lineMainSize, boolean isFlexShrink) {
		float remainingFreeSpace = lineMainSize - sumHypotheticalMainSizes(flexItems);
		if (remainingFreeSpace != 0 && !isFlexShrink) {
			float dividedFreeSpace = remainingFreeSpace / sumFlexGrowFactors(flexItems);
			distributeFreeSpace(flexItems, dividedFreeSpace);
		} else if (remainingFreeSpace != 0 && isFlexShrink) {
			float scaledShrinkFactor = sumScaledShrinkFactors(flexItems);
			undistributeFreeSpace(flexItems, remainingFreeSpace, scaledShrinkFactor);
		}

		// We don't yet support min/max main size constraints, so we just freeze all items
		freezeAllItems(flexItems);
	}

	private static float sumFlexGrowFactors(List<FlexItem> flexItems) {
		return flexItems.stream()
			.filter(flexItem -> !flexItem.isFrozen())
			.map(FlexItem::getFlexGrow)
			.reduce(0f, Float::sum);
	}

	private static float sumHypotheticalMainSizes(List<FlexItem> flexItems) {
		float totalSize = 0;
		for (FlexItem flexItem : flexItems) {
			totalSize += flexItem.getHypotheticalMainSize();
		}

		return totalSize;
	}

	private static void freezeInflexibleItems(List<FlexItem> flexItems, float hypotheticalMainSize, boolean isFlexShrink) {
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;

			float baseSize = flexItem.getBaseSize();
			float hypotheticalMainSizeOfItem = flexItem.getHypotheticalMainSize();
			if (
				(isFlexShrink && flexItem.getFlexShrink() == 0) ||
				(!isFlexShrink && flexItem.getFlexGrow() == 0) ||
				(!isFlexShrink && baseSize > hypotheticalMainSizeOfItem) ||
				(isFlexShrink && baseSize < hypotheticalMainSizeOfItem)
			) {
				flexItem.setMainSize(flexItem.getHypotheticalMainSize());
				flexItem.setFrozen(true);
			}
		}
	}

	private static void freezeAllItems(List<FlexItem> flexItems) {
		for (FlexItem flexItem : flexItems) {
			flexItem.setFrozen(true);
			flexItem.setMainSize(flexItem.getHypotheticalMainSize());
		}
	}

	private static void distributeFreeSpace(List<FlexItem> flexItems, float dividedFreeSpace) {
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;

			float usedFreeSpace = flexItem.getFlexGrow() * dividedFreeSpace;
			flexItem.setHypotheticalMainSize(flexItem.getHypotheticalMainSize() + usedFreeSpace);
		}
	}

	private static void undistributeFreeSpace(List<FlexItem> flexItems, float remainingFreeSpace, float scaledShrinkFactor) {
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;

			float scaledShrinkFactorOfItem = getScaledShrinkFactor(flexItem);
			float scaledShrinkRatio = scaledShrinkFactorOfItem / scaledShrinkFactor;
			float flexShrinkSpace = remainingFreeSpace * scaledShrinkRatio;
			float flexShrinkSize = flexItem.getHypotheticalMainSize() + flexShrinkSpace;
			flexItem.setHypotheticalMainSize(flexShrinkSize);
		}
	}

	private static float sumScaledShrinkFactors(List<FlexItem> flexItems) {
		float scaledShrinkFactorTotal = 0;
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;
			scaledShrinkFactorTotal += getScaledShrinkFactor(flexItem);
		}

		return scaledShrinkFactorTotal;
	}

	private static float getScaledShrinkFactor(FlexItem flexItem) {
		return flexItem.getBaseSize() * flexItem.getFlexShrink();
	}

}
