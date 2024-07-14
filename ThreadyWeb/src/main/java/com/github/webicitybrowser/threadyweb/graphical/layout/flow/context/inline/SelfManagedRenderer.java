package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class SelfManagedRenderer {
	
	private SelfManagedRenderer() {}

	public static RenderedUnit renderSelfManaged(
		FlowConfig flowConfig, LayoutRenderContext layoutRenderContext, Box childBox
	) {
		LocalRenderContext localRenderContext = layoutRenderContext.localRenderContext();
		GlobalRenderContext globalRenderContext = layoutRenderContext.globalRenderContext();

		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(
			layoutRenderContext, childBox.componentUI());
		BoxOffsetDimensions boxOffsetDimensions = getBoxOffsetDimensions(childBox.styleDirectives(), sizeCalculationContext);
		AbsoluteSize preferredSize = computePreferredSize(sizeCalculationContext, childBox, boxOffsetDimensions);
		AbsoluteSize containerSize = new AbsoluteSize(localRenderContext.preferredSize().width(), RelativeDimension.UNBOUNDED);
		AbsoluteSize contentSize = LayoutSizeUtils.subtractPadding(preferredSize, boxOffsetDimensions.padding());
		RenderedUnit childUnit = renderChildUnit(globalRenderContext, childBox, contentSize);
		if (childUnit.fitSize().width() > containerSize.width() && preferredSize.width() == RelativeDimension.UNBOUNDED) {
			float[] padding = boxOffsetDimensions.padding();
			AbsoluteSize adjustedContentSize = new AbsoluteSize(
				containerSize.width() - padding[0] - padding[1], contentSize.height());
			// TODO: This handles floats incorrectly
			childUnit = renderChildUnit(globalRenderContext, childBox, adjustedContentSize);
		}
		AbsoluteSize rawChildSize = childUnit.fitSize();
		AbsoluteSize outerSize = LayoutSizeUtils.addPadding(rawChildSize, boxOffsetDimensions.padding());
		AbsoluteSize adjustedOuterSize = LayoutSizeUtils.enforceSize(outerSize, preferredSize);

		StyledUnitContext styledUnitContext = new StyledUnitContext(childUnit, adjustedOuterSize, boxOffsetDimensions);
		RenderedUnit styledUnit = flowConfig.styledUnitGenerator().generateStyledUnit(styledUnitContext);

		return styledUnit;
	}

	private static BoxOffsetDimensions getBoxOffsetDimensions(DirectivePool styleDirectives, SizeCalculationContext sizeCalculationContext) {
		float[] padding = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, styleDirectives);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, styleDirectives);
		return new BoxOffsetDimensions(new float[4], padding, borders);
	}

	private static AbsoluteSize computePreferredSize(
		SizeCalculationContext sizeCalculationContext, Box childBox, BoxOffsetDimensions boxDimensions
	) {
		LayoutSizingContext layoutSizingContext = LayoutSizeUtils.createLayoutSizingContext(
			childBox.styleDirectives(), sizeCalculationContext, boxDimensions
		);
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(GlobalRenderContext globalRenderContext, Box childBox, AbsoluteSize contentSize) {
		LocalRenderContext childLocalRenderContext = new LocalRenderContext(contentSize, new ContextSwitch[0]);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

}