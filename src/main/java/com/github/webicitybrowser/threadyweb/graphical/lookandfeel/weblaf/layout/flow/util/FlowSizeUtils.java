package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.BoxSizingDirective.BoxSizing;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowSizeUtils {
	
	private FlowSizeUtils() {}

	public static AbsoluteSize computePreferredSize(
		DirectivePool styleDirectives, Function<Boolean, SizeCalculationContext> contextGenerator, float[] padding
	) {
		SizeCalculation widthSizeCalculation = WebDirectiveUtil.getWidth(styleDirectives);
		float calculatedWidth = widthSizeCalculation.calculate(contextGenerator.apply(true));
		SizeCalculation heightSizeCalculation = WebDirectiveUtil.getHeight(styleDirectives);
		float calculatedHeight = heightSizeCalculation.calculate(contextGenerator.apply(false));

		if (getBoxSizing(styleDirectives) == BoxSizing.CONTENT_BOX) {
			if (calculatedWidth != RelativeDimension.UNBOUNDED) {
				calculatedWidth += padding[0] + padding[1];
			}
			if (calculatedHeight != RelativeDimension.UNBOUNDED) {
				calculatedHeight += padding[2] + padding[3];
			}
		}

		return new AbsoluteSize(calculatedWidth, calculatedHeight);
	}

	public static AbsoluteSize enforcePreferredSize(
		AbsoluteSize rawChildSize, AbsoluteSize contentSize, AbsoluteSize preferredSize
	) {
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			contentSize.width() :
			rawChildSize.width();

		float heightComponent = preferredSize.height() != RelativeDimension.UNBOUNDED ?
			contentSize.height() :
			rawChildSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
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

	private static BoxSizing getBoxSizing(DirectivePool styleDirectives) {
		return styleDirectives
			.inheritDirectiveOrEmpty(BoxSizingDirective.class)
			.map(BoxSizingDirective::getValue)
			.orElse(BoxSizing.CONTENT_BOX);
	}

}
