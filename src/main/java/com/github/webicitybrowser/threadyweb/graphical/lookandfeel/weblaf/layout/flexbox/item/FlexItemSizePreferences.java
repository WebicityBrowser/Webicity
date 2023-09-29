package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.item;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.FlexMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.FlexUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.BoxPositioningOverride;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.position.PositionOffsetUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class FlexItemSizePreferences {
	
	private final DirectivePool styleDirectives;
	private final LayoutSizingContext sizingContext;
	private final BoxPositioningOverride boxPositioningOverride;

	public FlexItemSizePreferences(Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator = 
			isHorizontal -> FlexUtils.createSizeCalculationContext(globalRenderContext, localRenderContext, isHorizontal);
		this.styleDirectives = box.styleDirectives();
		SizeCalculationContext sizeCalculationContext = sizeCalculationContextGenerator.apply(true);
		float[] margins = FlexMarginCalculations.computeMargins(globalRenderContext, localRenderContext, box);
		float[] padding = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, box);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, box);
		BoxOffsetDimensions boxOffsetDimensions = new BoxOffsetDimensions(margins, padding, borders);
		this.sizingContext = LayoutSizeUtils.createLayoutSizingContext(styleDirectives, sizeCalculationContextGenerator, boxOffsetDimensions);
		this.boxPositioningOverride = PositionOffsetUtil.getPositioningOverride(sizeCalculationContextGenerator, box);
	}
	
	public BoxOffsetDimensions getBoxOffsetDimensions() {
		return sizingContext.boxOffsetDimensions();
	}

	public BoxPositioningOverride getBoxPositioningOverride() {
		return boxPositioningOverride;
	}

	public float getMainSize(FlexDirection flexDirection) {
		if (flexDirection.isHorizontal()) {
			return LayoutSizeUtils.computePreferredWidth(styleDirectives, sizingContext);
		} else {
			return LayoutSizeUtils.computePreferredHeight(styleDirectives, sizingContext);
		}
	}

	public float getMaxMainSize(FlexDirection flexDirection) {
		if (flexDirection.isHorizontal()) {
			return LayoutSizeUtils.computeMaxWidth(styleDirectives, sizingContext);
		} else {
			return LayoutSizeUtils.computeMaxHeight(styleDirectives, sizingContext);
		}
	}

	public float getMinMainSize(FlexDirection flexDirection) {
		if (flexDirection.isHorizontal()) {
			return LayoutSizeUtils.computeMinWidth(styleDirectives, sizingContext);
		} else {
			return LayoutSizeUtils.computeMinHeight(styleDirectives, sizingContext);
		}
	}

}
