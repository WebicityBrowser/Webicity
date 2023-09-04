package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import java.util.function.Function;

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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;
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
		AbsoluteSize adjustedSize = adjustAnonSize(state, preferredSize, childUnit.fitSize());
		AbsolutePosition childPosition = state.positionTracker().addBox(adjustedSize, new float[4]);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		state.addChildLayoutResult(new ChildLayoutResult(childUnit, childRect));
	}

	private static void renderChild(FlowBlockRendererState state, Box childBox) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float[] margins = FlowBlockMarginCalculations.computeMargins(state, childBox);
		float[] paddings = FlowPaddingCalculations.computePaddings(state.flowContext(), childBox);
		AbsoluteSize preferredSize = computePreferredSize(state, childBox, paddings);
		AbsoluteSize effectivePreferredSize = computeFallbackPreferredSize(state, preferredSize, margins);
		AbsoluteSize contentSize = FlowSizeUtils.subtractPadding(effectivePreferredSize, paddings);
		RenderedUnit childUnit = renderChildUnit(state, childBox, contentSize);
		AbsoluteSize rawChildSize = childUnit.fitSize();
		AbsoluteSize adjustedChildSize = FlowSizeUtils.enforcePreferredSize(rawChildSize, contentSize, preferredSize);
		AbsoluteSize adjustedSize = FlowSizeUtils.addPadding(adjustedChildSize, paddings);
		adjustedSize = stretchToParentSize(adjustedSize, parentSize, preferredSize, margins);
		float[] adjustedMargins = FlowBlockMarginCalculations.adjustMargins(state, margins, adjustedSize);
		adjustedMargins = FlowBlockMarginCalculations.collapseOverflowMargins(parentSize, adjustedSize, adjustedMargins);
		
		AbsolutePosition childPosition = state.positionTracker().addBox(adjustedSize, adjustedMargins);
		Rectangle childRect = new Rectangle(childPosition, adjustedSize);
		StyledUnitContext styledUnitContext = new StyledUnitContext(childBox, childUnit, adjustedSize, paddings);
		RenderedUnit styledUnit = state.flowContext().styledUnitGenerator().generateStyledUnit(styledUnitContext);
		state.addChildLayoutResult(new ChildLayoutResult(styledUnit, childRect));
	}

	private static AbsoluteSize computePreferredSize(FlowBlockRendererState state, Box childBox, float[] paddings) {
		if (childBox instanceof BasicAnonymousFluidBox) {
			return new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
		}

		FontMetrics fontMetrics = state.getFont().getMetrics();
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator =
			isHorizontal -> FlowUtils.createSizeCalculationContext(state.flowContext(), fontMetrics, isHorizontal);

		return FlowSizeUtils.computePreferredSize(childBox.styleDirectives(), sizeCalculationContextGenerator, paddings);
	}

	private static RenderedUnit renderChildUnit(FlowBlockRendererState state, Box childBox, AbsoluteSize contentSize) {
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		LocalRenderContext childLocalRenderContext = createChildLocalRenderContext(state, contentSize);
		return UIPipeline.render(childBox, globalRenderContext, childLocalRenderContext);
	}

	private static AbsoluteSize computeFallbackPreferredSize(FlowBlockRendererState state, AbsoluteSize preferredSize, float[] margins) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		float marginOffset = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		float actualPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			parentSize.width() == RelativeDimension.UNBOUNDED ?
				RelativeDimension.UNBOUNDED :
				Math.max(0, parentSize.width() - marginOffset);
		float actualPreferredHeight = preferredSize.height();

		return new AbsoluteSize(actualPreferredWidth, actualPreferredHeight);
	}

	private static AbsoluteSize stretchToParentSize(
		AbsoluteSize adjustedSize, AbsoluteSize parentSize, AbsoluteSize preferredSize, float[] margins
	) {
		if (margins[0] == RelativeDimension.UNBOUNDED || margins[1] == RelativeDimension.UNBOUNDED) {
			return adjustedSize;
		}
		float marginOffset = Math.max(0, margins[0]) + Math.max(0, margins[1]);
		float stretchedPreferredWidth = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			adjustedSize.width() :
			Math.max(parentSize.width() - marginOffset, adjustedSize.width());
		float stretchedPreferredHeight = adjustedSize.height();

		return new AbsoluteSize(stretchedPreferredWidth, stretchedPreferredHeight);
	}

	private static AbsoluteSize adjustAnonSize(
		FlowBlockRendererState state, AbsoluteSize preferredSize, AbsoluteSize fitSize
	) {
		AbsoluteSize parentSize = state.getLocalRenderContext().getPreferredSize();
		
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			preferredSize.width() :
			Math.max(parentSize.width(), fitSize.width());

		float heightComponent = preferredSize.height() != RelativeDimension.UNBOUNDED ?
			preferredSize.height() :
			fitSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowBlockRendererState state, AbsoluteSize preferredSize) {
		return LocalRenderContext.create(preferredSize, state.getFont().getMetrics(), new ContextSwitch[0]);
	}

}
