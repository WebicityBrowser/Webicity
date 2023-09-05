package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective.BoxSizing;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class LayoutSizeUtils {
	
	private LayoutSizeUtils() {}

	public static LayoutSizingContext createLayoutSizingContext(
		DirectivePool styleDirectives, Function<Boolean, SizeCalculationContext> contextGenerator, float[] padding
	) {
		BoxSizing boxSizing = getBoxSizing(styleDirectives);
		return new LayoutSizingContext(boxSizing, contextGenerator, padding);
	}

	public static AbsoluteSize computePreferredSize(
		DirectivePool styleDirectives, LayoutSizingContext layoutSizingContext
	) {
		SizeCalculation widthSizeCalculation = WebDirectiveUtil.getWidth(styleDirectives);
		float calculatedWidth = computeSize(widthSizeCalculation, layoutSizingContext, true);
		SizeCalculation heightSizeCalculation = WebDirectiveUtil.getHeight(styleDirectives);
		float calculatedHeight = computeSize(heightSizeCalculation, layoutSizingContext, false);

		return new AbsoluteSize(calculatedWidth, calculatedHeight);
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

	private static float computeSize(
		SizeCalculation sizeCalculation, LayoutSizingContext layoutSizingContext, boolean isWidth
	) {
		SizeCalculationContext sizeCalculationContext = layoutSizingContext.contextGenerator().apply(isWidth);
		float[] padding = layoutSizingContext.padding();
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
		BoxSizing boxSizing, Function<Boolean, SizeCalculationContext> contextGenerator, float[] padding
	) {};

}
