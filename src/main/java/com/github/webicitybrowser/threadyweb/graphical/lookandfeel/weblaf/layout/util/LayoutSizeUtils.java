package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective.BoxSizing;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebSizeDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutSizeUtils {
	
	private LayoutSizeUtils() {}

	public static LayoutSizingContext createLayoutSizingContext(
		DirectivePool styleDirectives, Function<Boolean, SizeCalculationContext> contextGenerator, BoxOffsetDimensions boxDimensions
	) {
		BoxSizing boxSizing = getBoxSizing(styleDirectives);
		return new LayoutSizingContext(boxDimensions, boxSizing, contextGenerator);
	}

	public static AbsoluteSize computePreferredSize(
		DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext
	) {
		float width = computePreferredWidth(styleDirectives, layoutSizingContext);
		float height = computePreferredHeight(styleDirectives, layoutSizingContext);
		return new AbsoluteSize(width, height);
	}

	public static float computePreferredWidth(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation widthSizeCalculation = WebSizeDirectiveUtil.getWidth(styleDirectives);
		return computeSize(widthSizeCalculation, layoutSizingContext, true);
	}

	public static float computeMaxWidth(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation maxWidthSizeCalculation = WebSizeDirectiveUtil.getMaxWidth(styleDirectives);
		return computeSize(maxWidthSizeCalculation, layoutSizingContext, true);
	}

	public static float computeMinWidth(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation minWidthSizeCalculation = WebSizeDirectiveUtil.getMinWidth(styleDirectives);
		return computeSize(minWidthSizeCalculation, layoutSizingContext, true);
	}

	public static float computePreferredHeight(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation heightSizeCalculation = WebSizeDirectiveUtil.getHeight(styleDirectives);
		return computeSize(heightSizeCalculation, layoutSizingContext, false);
	}

	public static float computeMaxHeight(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation maxHeightSizeCalculation = WebSizeDirectiveUtil.getMaxHeight(styleDirectives);
		return computeSize(maxHeightSizeCalculation, layoutSizingContext, false);
	}

	public static float computeMinHeight(DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext) {
		SizeCalculation minHeightSizeCalculation = WebSizeDirectiveUtil.getMinHeight(styleDirectives);
		return computeSize(minHeightSizeCalculation, layoutSizingContext, false);
	}

	public static AbsoluteSize subtractPadding(AbsoluteSize initialSize, float[] paddings) {
		float widthComponent = initialSize.width() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			Math.max(0, initialSize.width() - paddings[0] - paddings[1]);
		float heightComponent = initialSize.height() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			Math.max(0, initialSize.height() - paddings[2] - paddings[3]);

		return new AbsoluteSize(widthComponent, heightComponent);
	}

	public static AbsoluteSize addPadding(AbsoluteSize initialSize, float[] paddings) {
		float widthComponent = initialSize.width() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			initialSize.width() + paddings[0] + paddings[1];
		float heightComponent = initialSize.height() == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			initialSize.height() + paddings[2] + paddings[3];

		return new AbsoluteSize(widthComponent, heightComponent);
	}

	public static SizeCalculationContext createSizeCalculationContext(LayoutManagerContext context, boolean isHorizontal) {
		return createSizeCalculationContext(context, context.localRenderContext().getParentFontMetrics(), isHorizontal);
	}

	public static SizeCalculationContext createSizeCalculationContext(LayoutManagerContext context, FontMetrics parentFontMetrics, boolean isHorizontal) {
		return createSizeCalculationContext(context.globalRenderContext(), context.localRenderContext(), parentFontMetrics, isHorizontal);
	}

	public static SizeCalculationContext createSizeCalculationContext(
		GlobalRenderContext context, LocalRenderContext localRenderContext, boolean isHorizontal
	) {
		return createSizeCalculationContext(context, localRenderContext, localRenderContext.getParentFontMetrics(), isHorizontal);
	}

	public static SizeCalculationContext createSizeCalculationContext(
		GlobalRenderContext context, LocalRenderContext localRenderContext, FontMetrics parentFontMetrics, boolean isHorizontal
	) {
		return new SizeCalculationContext(
			localRenderContext.getPreferredSize(),
			context.viewportSize(),
			parentFontMetrics,
			context.rootFontMetrics(),
			isHorizontal);
	}

	private static float computeSize(
		SizeCalculation sizeCalculation, LayoutSizingContext layoutSizingContext, boolean isWidth
	) {
		SizeCalculationContext sizeCalculationContext = layoutSizingContext.contextGenerator().apply(isWidth);
		float[] padding = layoutSizingContext.boxOffsetDimensions().totalPadding();
		float directionalPadding = isWidth ? padding[0] + padding[1] : padding[2] + padding[3];
		
		float calculatedSize = sizeCalculation.calculate(sizeCalculationContext);
		if (
			layoutSizingContext.boxSizing() == BoxSizing.CONTENT_BOX &&
			calculatedSize != RelativeDimension.UNBOUNDED
		) {
			calculatedSize += directionalPadding;
		}

		return calculatedSize;
	}

	private static BoxSizing getBoxSizing(DirectivePool styleDirectives) {
		return styleDirectives
			.inheritDirectiveOrEmpty(BoxSizingDirective.class)
			.map(BoxSizingDirective::getValue)
			.orElse(BoxSizing.CONTENT_BOX);
	}

	public static record LayoutSizingContext(
		BoxOffsetDimensions boxOffsetDimensions, BoxSizing boxSizing, Function<Boolean, SizeCalculationContext> contextGenerator
	) {};

}
