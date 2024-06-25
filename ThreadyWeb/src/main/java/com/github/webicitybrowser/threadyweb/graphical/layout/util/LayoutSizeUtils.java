package com.github.webicitybrowser.threadyweb.graphical.layout.util;

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
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.SizeCalculationDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebDirectiveUtil;
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
		float width = computeSize(styleDirectives, WidthDirective.class, layoutSizingContext);
		float height = computeSize(styleDirectives, HeightDirective.class, layoutSizingContext);
		return new AbsoluteSize(width, height);
	}

	public static <T extends SizeCalculationDirective> float computeSize(DirectivePool styleDirectives, Class<T> directiveType, LayoutSizingContext layoutSizingContext) {
		
		SizeCalculation widthSizeCalculation = WebDirectiveUtil.getSizeCalculation(styleDirectives, directiveType);
		boolean isWidth =
			directiveType == WidthDirective.class
			|| directiveType == MinWidthDirective.class
			|| directiveType == MaxHeightDirective.class;
		return computeSize(widthSizeCalculation, layoutSizingContext, isWidth);
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

	public static SizeCalculationContext createSizeCalculationContext(LayoutManagerContext context, DirectivePool directives, boolean isHorizontal) {
		return createSizeCalculationContext(context.globalRenderContext(), context.localRenderContext(), directives, isHorizontal);
	}

	public static SizeCalculationContext createSizeCalculationContext(
		GlobalRenderContext context, LocalRenderContext localRenderContext, DirectivePool parentDirectives, boolean isHorizontal
	) {
		FontMetrics fontMetrics = WebFontUtil.getFont(parentDirectives, context).getMetrics();
		return createSizeCalculationContext(context, localRenderContext, fontMetrics, isHorizontal);
	}

	private static SizeCalculationContext createSizeCalculationContext(
		GlobalRenderContext context, LocalRenderContext localRenderContext, FontMetrics fontMetrics, boolean isHorizontal
	) {
		return new SizeCalculationContext(
			localRenderContext.preferredSize(),
			context.viewportSize(),
			fontMetrics,
			context.rootFontMetrics(),
			isHorizontal);
	}

	private static float computeSize(
		SizeCalculation sizeCalculation, LayoutSizingContext layoutSizingContext, boolean isWidth
	) {
		SizeCalculationContext sizeCalculationContext = layoutSizingContext.sizeCalculationContextGenerator().apply(isWidth);
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
		BoxOffsetDimensions boxOffsetDimensions, BoxSizing boxSizing, Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator
	) {};

}
