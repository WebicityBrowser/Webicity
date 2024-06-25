package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;

public final class FlexMainSizeDetermination {

	private FlexMainSizeDetermination() {}

	public static List<FlexLine> determineLinesWithMainSizes(LayoutManagerContext layoutManagerContext, List<FlexItem> flexItems) {
		FlexWrap flexWrap = FlexUtils.getFlexWrap(layoutManagerContext.layoutDirectives());
		switch (flexWrap) {
		case NOWRAP:
			return determineLinesWithMainSizesNoWrap(layoutManagerContext, flexItems);
		case WRAP:
		case WRAP_REVERSE:
			return determineLinesWithMainSizesWrap(layoutManagerContext, flexItems);
		default:
			throw new IllegalArgumentException("Unknown flex wrap: " + flexWrap);
		}
	}

	private static List<FlexLine> determineLinesWithMainSizesNoWrap(LayoutManagerContext layoutManagerContext, List<FlexItem> flexItems) {
		FlexDirection flexDirection = FlexUtils.getFlexDirection(layoutManagerContext.layoutDirectives());
		FlexLine flexLine = new FlexLine(flexDirection);
		setLineInitialMainSize(layoutManagerContext, flexLine, flexDirection);
			for (FlexItem flexItem: flexItems) {
			determineMainSize(layoutManagerContext, flexItem, flexDirection);
			flexLine.addFlexItem(flexItem);
		}

		return List.of(flexLine);
	}

	private static List<FlexLine> determineLinesWithMainSizesWrap(LayoutManagerContext layoutManagerContext, List<FlexItem> flexItems) {
		DirectivePool layoutDirectives = layoutManagerContext.layoutDirectives();
		FlexDirection flexDirection = FlexUtils.getFlexDirection(layoutDirectives);
		AbsoluteSize preferredSize = layoutManagerContext.localRenderContext().preferredSize();
		for (FlexItem flexItem: flexItems) {
			determineMainSize(layoutManagerContext, flexItem, FlexUtils.getFlexDirection(layoutDirectives));
		}

		List<FlexLine> flexLines = new ArrayList<>();
		float availableMainSpace = FlexDimension.createFrom(preferredSize, flexDirection).main();
		for (int i = 0; i < flexItems.size();) {
			FlexLine flexLine = new FlexLine(flexDirection);
			setLineInitialMainSize(layoutManagerContext, flexLine, flexDirection);

			FlexItem flexItem = flexItems.get(i);
			flexLine.addFlexItem(flexItem);
			float remainingMainSpace = availableMainSpace - flexItem.getHypotheticalMainSize();
			
			i++;
			for (; i < flexItems.size(); i++) {
				flexItem = flexItems.get(i);
				float hypotheticalMainSize = flexItem.getHypotheticalMainSize();
				if (availableMainSpace != RelativeDimension.UNBOUNDED && hypotheticalMainSize > remainingMainSpace) break;
				flexLine.addFlexItem(flexItem);
				remainingMainSpace -= hypotheticalMainSize;
			}
			flexLines.add(flexLine);
		}

		return flexLines;
	}

	private static void setLineInitialMainSize(LayoutManagerContext layoutManagerContext, FlexLine flexLine, FlexDirection flexDirection) {
		AbsoluteSize preferredSize = layoutManagerContext.localRenderContext().preferredSize();
		float mainSize = FlexDimension.createFrom(preferredSize, flexDirection).main();
		if (mainSize != RelativeDimension.UNBOUNDED) {
			flexLine.setMainSize(mainSize);
		}
	}

	private static void determineMainSize(
		LayoutManagerContext layoutManagerContext, FlexItem flexItem, FlexDirection flexDirection
	) {
		// TODO: Check flex basis
		float preferredWidth = flexItem.getSizePreferences().getMainSize(flexDirection);
		if (preferredWidth != RelativeDimension.UNBOUNDED) {
			flexItem.setBaseSize(preferredWidth);
			flexItem.setHypotheticalMainSize(clampMinMax(preferredWidth, flexItem, flexDirection));
			return;
		}
		
		GlobalRenderContext globalRenderContext = layoutManagerContext.globalRenderContext();
		FlexItemRenderer.FlexItemRenderContext flexItemRenderContext = new FlexItemRenderer.FlexItemRenderContext(
			globalRenderContext, flexDirection);
		AbsoluteSize fitSize = FlexItemRenderer.render(flexItem, flexItemRenderContext);
		FlexDimension flexDimension = FlexDimension.createFrom(fitSize, flexDirection);
		flexItem.setBaseSize(flexDimension.main());
		flexItem.setHypotheticalMainSize(clampMinMax(flexDimension.main(), flexItem, flexDirection));
	}

	private static float clampMinMax(float main, FlexItem flexItem, FlexDirection flexDirection) {
		float maxMainSize = flexItem.getSizePreferences().getMaxMainSize(flexDirection);
		if (maxMainSize != RelativeDimension.UNBOUNDED) {
			main = Math.min(main, maxMainSize);
		}

		float minMainSize = flexItem.getSizePreferences().getMinMainSize(flexDirection);
		if (minMainSize != RelativeDimension.UNBOUNDED) {
			main = Math.max(main, minMainSize);
		}

		return Math.max(0, main);
	}

}
