package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;

public final class Flexer {
	
	private Flexer() {}

	public static void resolveFlexibleLengths(FlexLine flexLine, LocalRenderContext localRenderContext) {
		float lineMainSize = FlexDimension
			.createFrom(localRenderContext.preferredSize(), flexLine.getFlexDirection())
			.main();

		List<FlexItem> flexItems = flexLine.getFlexItems();
		if (lineMainSize == RelativeDimension.UNBOUNDED) {
			// TODO: Handle infinite sizes properly
			freezeAllItems(flexItems);
			return;
		}

		boolean isFlexShrink = sumHypotheticalMainSizes(flexItems) > lineMainSize;

		freezeInflexibleItems(flexItems, isFlexShrink);
		while (!flexItems.stream().allMatch(FlexItem::isFrozen)) {
			peformFlex(flexLine, lineMainSize, isFlexShrink);
		}
	}

	private static void peformFlex(FlexLine flexLine, float lineMainSize, boolean isFlexShrink) {
		distributeSpaceToFlexibleItems(flexLine.getFlexItems(), lineMainSize, isFlexShrink);
		resolveMinMaxViolations(flexLine, flexLine.getFlexDirection());
	}

	private static void resolveMinMaxViolations(FlexLine flexLine, FlexDirection flexDirection) {
		List<FlexItem> flexItems = flexLine.getFlexItems();
		float minMaxViolations = sumMinMaxViolations(flexItems, flexLine.getFlexDirection());
		if (minMaxViolations == 0) {
			freezeAllItems(flexItems);
		} else if (minMaxViolations > 0) {
			freezeViolations(flexItems, flexLine.getFlexDirection(), false);
		} else {
			freezeViolations(flexItems, flexLine.getFlexDirection(), true);
		}
	}

	private static void distributeSpaceToFlexibleItems(List<FlexItem> flexItems, float lineMainSize, boolean isFlexShrink) {
		float remainingFreeSpace = lineMainSize - sumHypotheticalMainSizes(flexItems);
		if (remainingFreeSpace != 0 && !isFlexShrink) {
			float dividedFreeSpace = remainingFreeSpace / sumFlexGrowFactors(flexItems);
			distributeFreeSpace(flexItems, dividedFreeSpace);
		} else if (remainingFreeSpace != 0) {
			float scaledShrinkFactor = sumScaledShrinkFactors(flexItems);
			undistributeFreeSpace(flexItems, remainingFreeSpace, scaledShrinkFactor);
		}
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

	private static void freezeInflexibleItems(List<FlexItem> flexItems, boolean isFlexShrink) {
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;

			if (isInflexible(flexItem, isFlexShrink)) {
				flexItem.setMainSize(flexItem.getHypotheticalMainSize());
				flexItem.setFrozen(true);
			}
		}
	}

	private static boolean isInflexible(FlexItem flexItem, boolean isFlexShrink) {
		boolean canShrink = isFlexShrink && flexItem.getFlexShrink() != 0;
		boolean canGrow = !isFlexShrink && flexItem.getFlexGrow() != 0;
		boolean shrinkedTooMuch = isFlexShrink && flexItem.getHypotheticalMainSize() < flexItem.getBaseSize();
		boolean grownTooMuch = !isFlexShrink && flexItem.getHypotheticalMainSize() > flexItem.getBaseSize();
		return !canShrink && !canGrow || shrinkedTooMuch || grownTooMuch;
	}

	private static void freezeAllItems(List<FlexItem> flexItems) {
		for (FlexItem flexItem : flexItems) {
			flexItem.setFrozen(true);
			flexItem.setMainSize(flexItem.getHypotheticalMainSize());
		}
	}

	private static void freezeViolations(List<FlexItem> flexItems, FlexDirection flexDirection, boolean isMax) {
		for (FlexItem flexItem : flexItems) {
			if (flexItem.isFrozen()) continue;

			float minSize = flexItem.getSizePreferences().getMinMainSize(flexDirection);
			float maxSize = flexItem.getSizePreferences().getMaxMainSize(flexDirection);
			float hypotheticalMainSize = flexItem.getHypotheticalMainSize();
			if (minSize != RelativeDimension.UNBOUNDED && hypotheticalMainSize < minSize) {
				flexItem.setHypotheticalMainSize(minSize);
				flexItem.setFrozen(!isMax);
			} else if (maxSize != RelativeDimension.UNBOUNDED && hypotheticalMainSize > maxSize) {
				flexItem.setHypotheticalMainSize(maxSize);
				flexItem.setFrozen(isMax);
			}
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

	private static float sumMinMaxViolations(List<FlexItem> flexItems, FlexDirection flexDirection) {
		float minMaxViolations = 0;
		for (FlexItem item: flexItems) {
			if (item.isFrozen()) continue;

			float minSize = item.getSizePreferences().getMinMainSize(flexDirection);
			float maxSize = item.getSizePreferences().getMaxMainSize(flexDirection);
			float hypotheticalMainSize = item.getHypotheticalMainSize();
			if (minSize != RelativeDimension.UNBOUNDED && hypotheticalMainSize < minSize) {
				minMaxViolations += minSize - hypotheticalMainSize;
			} else if (maxSize != RelativeDimension.UNBOUNDED && hypotheticalMainSize > maxSize) {
				minMaxViolations += maxSize - hypotheticalMainSize;
			}
		}

		return minMaxViolations;
	}

}
