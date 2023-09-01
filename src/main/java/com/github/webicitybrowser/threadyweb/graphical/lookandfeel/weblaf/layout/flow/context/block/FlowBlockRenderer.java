package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowBlockRenderer {
	
	private FlowBlockRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		Font2D font = FlowUtils.computeFont(
			context,
			context.box().styleDirectives(),
			context.localRenderContext().getParentFontMetrics());
		FlowBlockRendererState state = new FlowBlockRendererState(context, font);
		renderChildren(state, context.box());

		return LayoutResult.create(state.childLayoutResults(), state.positionTracker().fitSize());
	}

	private static void renderChildren(FlowBlockRendererState state, ChildrenBox box) {
		for (Box childBox: box.getChildrenTracker().getChildren()) {
			if (childBox instanceof BasicAnonymousFluidBox) {
				renderAnonBox(state, childBox);
			} else {
				renderChild(state, childBox);
			}
		}
	}

	private static void renderAnonBox(FlowBlockRendererState state, Box anonBox) {
		AbsoluteSize preferredSize = new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		RenderedUnit childUnit = UIPipeline.render(anonBox, globalRenderContext, localRenderContext);
		AbsoluteSize adjustedSize = adjustSize(state, preferredSize, childUnit.fitSize(), new float[4]);
		AbsolutePosition childPosition = state.positionTracker().addBox(adjustedSize, new float[4]);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, childRect));
	}

	private static void renderChild(FlowBlockRendererState state, Box childBox) {
		AbsoluteSize preferredSize = computePreferredSize(state, childBox);
		float[] margins = FlowBlockMarginCalculations.computeMargins(state, childBox);
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = createChildLocalRenderContext(state, preferredSize);
		RenderedUnit childUnit = UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
		AbsoluteSize adjustedSize = adjustSize(state, preferredSize, childUnit.fitSize(), margins);
		float[] adjustedMargins = FlowBlockMarginCalculations.adjustMargins(state, margins, adjustedSize);
		AbsolutePosition childPosition = state.positionTracker().addBox(adjustedSize, adjustedMargins);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, childRect));
	}

	private static AbsoluteSize computePreferredSize(FlowBlockRendererState state, Box childBox) {
		if (childBox instanceof BasicAnonymousFluidBox) {
			return new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
		}

		SizeCalculation widthSizeCalculation = WebDirectiveUtil.getWidth(childBox.styleDirectives());
		float calculatedWidth = computeSize(childBox, widthSizeCalculation, state, true);
		SizeCalculation heightSizeCalculation = WebDirectiveUtil.getHeight(childBox.styleDirectives());
		float calculatedHeight = computeSize(childBox, heightSizeCalculation, state, false);
		return new AbsoluteSize(calculatedWidth, calculatedHeight);
	}

	private static float computeSize(Box childBox, SizeCalculation sizeCalculation, FlowBlockRendererState state, boolean isHorizontal) {
		FontMetrics fontMetrics = state.getFont().getMetrics();
		SizeCalculationContext sizeCalculationContext = FlowUtils.createSizeCalculationContext(
			state.flowContext(), fontMetrics, isHorizontal);

		return sizeCalculation.calculate(sizeCalculationContext);
	}

	private static AbsoluteSize adjustSize(FlowBlockRendererState state, AbsoluteSize preferredSize, AbsoluteSize fitSize, float[] margins) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float recommendedSize =	parentSize.width() - margins[0] - margins[1];
		if (recommendedSize < 0) {
			recommendedSize = Math.max(0, parentSize.width() - margins[0]);
		}
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			Math.max(recommendedSize, fitSize.width());
		float heightComponent = Math.max(preferredSize.height(), fitSize.height());

		return new AbsoluteSize(widthComponent, heightComponent);
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		AbsoluteSize actualPreferredSize = computeActualPreferredSize(state, preferredSize);
		
		return LocalRenderContext.create(actualPreferredSize, state.getFont().getMetrics(), new ContextSwitch[0]);
	}

	private static AbsoluteSize computeActualPreferredSize(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float actualPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			parentSize.width();
		float actualPreferredHeight = preferredSize.height();

		return new AbsoluteSize(actualPreferredWidth, actualPreferredHeight);
	}

}
