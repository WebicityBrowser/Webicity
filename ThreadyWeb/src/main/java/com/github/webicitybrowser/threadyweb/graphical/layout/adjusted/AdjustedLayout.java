package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position.PositionOffsetCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position.PositionOffsetUtil;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockChildRenderResult;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockPrerenderSizingInfo;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderer;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block.FlowBlockUnitRenderingContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class AdjustedLayout {
	
	private AdjustedLayout() {}

	// TODO: Well, percentage relative positioning is just wrong, and a lot of work will be needed to support absolute positioning

	public static boolean isInFlow(Box box) {
		return
			PositionOffsetUtil.getPositionType(box.styleDirectives()) == PositionType.STATIC ||
			PositionOffsetUtil.getPositionType(box.styleDirectives()) == PositionType.RELATIVE;
	}

	public static ChildLayoutResult adjustLayoutResult(ChildLayoutResult originalLayoutResult, SizeCalculationContext sizeCalculationContext) {
		RenderedUnit unit = originalLayoutResult.unit();
		PositionType positionType = PositionOffsetUtil.getPositionType(unit.styleDirectives());
		if (positionType != PositionType.RELATIVE) return originalLayoutResult;

		AbsolutePosition positionOffset = PositionOffsetUtil.getRelativePositionOffset(sizeCalculationContext, unit.styleDirectives());
		AbsolutePosition adjustedPosition = AbsoluteDimensionsMath.sum(
			originalLayoutResult.relativeRect().position(), positionOffset, AbsolutePosition::new);
		ChildLayoutResult adjustedChildLayoutResult = new ChildLayoutResult(
			originalLayoutResult.unit(),
			new Rectangle(adjustedPosition, originalLayoutResult.relativeRect().size())
		);

		return adjustedChildLayoutResult;
	}

    public static ChildLayoutResult renderAdjustedBox(Box box, LayoutRenderContext layoutRenderContext, StyledUnitGenerator styledUnitGenerator) {
        PositionType positionType = PositionOffsetUtil.getPositionType(box.styleDirectives());
		return switch (positionType) {
			case ABSOLUTE -> null;
			case FIXED -> renderFixedBox(box, layoutRenderContext, styledUnitGenerator);
			case STICKY -> null;
			default -> throw new IllegalStateException("Can't handle position type: " + positionType + " in this renderer");
		};
	}

	private static ChildLayoutResult renderFixedBox(Box childBox, LayoutRenderContext layoutRenderContext, StyledUnitGenerator styledUnitGenerator) {
		DirectivePool styleDirectives = childBox.styleDirectives();
		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(layoutRenderContext, styleDirectives);
		float[] positions = PositionOffsetCalculations.calculateOffset(sizeCalculationContext, styleDirectives);

		BoxOffsetDimensions boxDimensions = BoxOffsetDimensions.create(layoutRenderContext, styleDirectives);
		AbsoluteSize viewportSize = layoutRenderContext.globalRenderContext().viewportSize();
		FlowBlockUnitRenderingContext context = new FlowBlockUnitRenderingContext(
			childBox, boxDimensions,
			childSize -> new LocalRenderContext(childSize, new ContextSwitch[0]),
			childSize -> computeFallbackPreferredSize(viewportSize, childSize, boxDimensions.margins(), positions));

		FlowBlockPrerenderSizingInfo prerenderSizingInfo = FlowBlockUnitRenderer.prerenderChild(layoutRenderContext, context, true);
		FlowBlockChildRenderResult childRenderResult = FlowBlockUnitRenderer.generateChildUnit(
			context, prerenderSizingInfo, layoutRenderContext.globalRenderContext());
		
		AbsoluteSize finalChildSize = LayoutSizeUtils.addPadding(childRenderResult.adjustedSize(), boxDimensions.totalPadding());
		AbsolutePosition fixedPosition = PositionOffsetUtil.getFixedPositionOffset(
			prerenderSizingInfo.sizingContext().sizeCalculationContext(),
			styleDirectives, finalChildSize);
		Rectangle fixedRect = new Rectangle(fixedPosition, finalChildSize);

		RenderedUnit styledUnit = styledUnitGenerator.generateStyledUnit(
			new StyledUnitContext(styleDirectives, childRenderResult.unit(), finalChildSize, boxDimensions)
		);
		AdjustedUnit adjustedUnit = new AdjustedUnit(styledUnit, fixedRect);

		return new ChildLayoutResult(adjustedUnit, new Rectangle(new AbsolutePosition(0, 0), AbsoluteSize.ZERO_SIZE));
	}

	private static AbsoluteSize computeFallbackPreferredSize(AbsoluteSize parentSize, AbsoluteSize preferredSize, float[] positions, float[] margins) {
		margins = LayoutMarginCalculations.zeroAutoMargins(margins);
		
		float marginOffsetX = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		float actualPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			parentSize.width() == RelativeDimension.UNBOUNDED ?
				RelativeDimension.UNBOUNDED :
				Math.max(0, parentSize.width() - marginOffsetX);
		if (
			actualPreferredWidth == RelativeDimension.UNBOUNDED
			&& positions[0] != RelativeDimension.UNBOUNDED
			&& positions[1] != RelativeDimension.UNBOUNDED
		) {
			actualPreferredWidth = Math.max(0, parentSize.width() - positions[0] - positions[1] - marginOffsetX);
		}

		float actualPreferredHeight = preferredSize.height();
		if (
			actualPreferredHeight == RelativeDimension.UNBOUNDED
			&& positions[2] != RelativeDimension.UNBOUNDED
			&& positions[3] != RelativeDimension.UNBOUNDED
		) {
			float marginOffsetY = Math.max(0, margins[2]) + Math.max(0, margins[3]);
			actualPreferredHeight = Math.max(0, parentSize.height() - positions[2] - positions[3] - marginOffsetY);
		}


		return new AbsoluteSize(actualPreferredWidth, actualPreferredHeight);
	}

}
