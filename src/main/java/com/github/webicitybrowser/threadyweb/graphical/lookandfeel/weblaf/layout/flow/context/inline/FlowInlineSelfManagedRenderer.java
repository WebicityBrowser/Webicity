package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowInlineSelfManagedRenderer {
	
	private FlowInlineSelfManagedRenderer() {}

	public static void addSelfManagedBoxToLine(FlowInlineRendererState state, Box childBox) {
		float[] padding = FlowPaddingCalculations.computePaddings(state.flowContext(), childBox);
		AbsoluteSize preferredSize = computePreferredSize(state, childBox, padding);
		AbsoluteSize contentSize = FlowSizeUtils.subtractPadding(preferredSize, padding);
		RenderedUnit childUnit = renderChildUnit(state, childBox, contentSize);
		AbsoluteSize rawChildSize = childUnit.fitSize();
		AbsoluteSize adjustedChildSize = FlowSizeUtils.enforcePreferredSize(rawChildSize, contentSize, preferredSize);
		AbsoluteSize adjustedSize = FlowSizeUtils.addPadding(adjustedChildSize, padding);

		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, adjustedSize, padding);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);

		FlowInlineRendererUtil.startNewLineIfNotFits(state, adjustedSize);
		state.lineContext().currentLine().add(styledUnit, adjustedSize);
	}

	private static AbsoluteSize computePreferredSize(FlowInlineRendererState state, Box childBox, float[] padding) {
		FontMetrics fontMetrics = state.getFontStack().peek().getMetrics();
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator = 
			isHorizontal -> FlowUtils.createSizeCalculationContext(state.flowContext(), fontMetrics, isHorizontal);
		return FlowSizeUtils.computePreferredSize(childBox.styleDirectives(), sizeCalculationContextGenerator, padding);
	}

	private static RenderedUnit renderChildUnit(FlowInlineRendererState state, Box childBox, AbsoluteSize contentSize) {
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = createChildLocalRenderContext(state, childBox, contentSize);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowInlineRendererState state, Box childBox, AbsoluteSize contentSize) {
		Font2D lastFont = state.getFontStack().peek();
		return LocalRenderContext.create(contentSize, lastFont.getMetrics(), new ContextSwitch[0]);
	}

}
