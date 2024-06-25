package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowInlineSelfManagedRenderer {
	
	private FlowInlineSelfManagedRenderer() {}

	public static void addSelfManagedBoxToLine(FlowInlineRendererState state, Box childBox) {
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator = 
			isHorizontal -> LayoutSizeUtils.createSizeCalculationContext(state.flowContext().layoutManagerContext(), childBox.styleDirectives(), isHorizontal);
		SizeCalculationContext sizeCalculationContext = sizeCalculationContextGenerator.apply(true);
		BoxOffsetDimensions boxOffsetDimensions = getBoxOffsetDimensions(childBox, sizeCalculationContext);
		AbsoluteSize preferredSize = computePreferredSize(sizeCalculationContextGenerator, childBox, boxOffsetDimensions);
		AbsoluteSize contentSize = LayoutSizeUtils.subtractPadding(preferredSize, boxOffsetDimensions.padding());
		RenderedUnit childUnit = renderChildUnit(state, childBox, contentSize);
		AbsoluteSize rawChildSize = childUnit.fitSize();
		AbsoluteSize adjustedChildSize = FlowSizeUtils.enforcePreferredSize(rawChildSize, contentSize);
		AbsoluteSize adjustedSize = LayoutSizeUtils.addPadding(adjustedChildSize, boxOffsetDimensions.padding());

		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, adjustedSize, boxOffsetDimensions);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);

		FlowInlineRendererUtil.startNewLineIfNotFits(state, adjustedSize);
		state.lineContext().currentLine().add(styledUnit, adjustedSize);
	}

	private static BoxOffsetDimensions getBoxOffsetDimensions(Box childBox, SizeCalculationContext sizeCalculationContext) {
		float[] padding = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, childBox);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, childBox);
		return new BoxOffsetDimensions(new float[4], padding, borders);
	}

	private static AbsoluteSize computePreferredSize(
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator, Box childBox, BoxOffsetDimensions boxDimensions
	) {
		LayoutSizingContext layoutSizingContext = LayoutSizeUtils.createLayoutSizingContext(
			childBox.styleDirectives(), sizeCalculationContextGenerator, boxDimensions
		);
		return LayoutSizeUtils.computePreferredSize(childBox.styleDirectives(), layoutSizingContext);
	}

	private static RenderedUnit renderChildUnit(FlowInlineRendererState state, Box childBox, AbsoluteSize contentSize) {
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = new LocalRenderContext(contentSize, new ContextSwitch[0]);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

}
