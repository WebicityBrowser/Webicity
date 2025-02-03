package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item;

import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexBasisDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class FlexItemSizePreferences {
	
	private final DirectivePool styleDirectives;
	private final LayoutSizingContext sizingContext;

	public FlexItemSizePreferences(LayoutRenderContext layoutManagerContext, Box box) {
		GlobalRenderContext globalRenderContext = layoutManagerContext.globalRenderContext();
		LocalRenderContext localRenderContext = layoutManagerContext.localRenderContext();
		this.styleDirectives = box.styleDirectives();

		SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(globalRenderContext, localRenderContext, styleDirectives);
		float[] margins = LayoutMarginCalculations.computeMargins(sizeCalculationContext, styleDirectives);
		float[] padding = LayoutPaddingCalculations.computePaddings(sizeCalculationContext, styleDirectives);
		float[] borders = LayoutBorderWidthCalculations.computeBorderWidths(sizeCalculationContext, styleDirectives);
		BoxOffsetDimensions boxOffsetDimensions = new BoxOffsetDimensions(margins, padding, borders);
		this.sizingContext = LayoutSizeUtils.createLayoutSizingContext(styleDirectives, sizeCalculationContext, boxOffsetDimensions);
	}
	
	public BoxOffsetDimensions getBoxOffsetDimensions() {
		return sizingContext.boxOffsetDimensions();
	}

	public float getMainSize(FlexDirection flexDirection) {
		return flexDirection.isHorizontal() ?
			LayoutSizeUtils.computeSize(styleDirectives, WidthDirective.class, sizingContext) :
			LayoutSizeUtils.computeSize(styleDirectives, HeightDirective.class, sizingContext);
	}

	public float getMaxMainSize(FlexDirection flexDirection) {
		return flexDirection.isHorizontal() ?
			LayoutSizeUtils.computeSize(styleDirectives, MaxWidthDirective.class, sizingContext) :
			LayoutSizeUtils.computeSize(styleDirectives, MaxHeightDirective.class, sizingContext);
	}

	public float getMinMainSize(FlexDirection flexDirection) {
		return flexDirection.isHorizontal() ?
			LayoutSizeUtils.computeSize(styleDirectives, MinWidthDirective.class, sizingContext) :
			LayoutSizeUtils.computeSize(styleDirectives, MinHeightDirective.class, sizingContext);
	}

	public float getCrossSize(FlexDirection flexDirection) {
		return flexDirection.isHorizontal() ?
			LayoutSizeUtils.computeSize(styleDirectives, HeightDirective.class, sizingContext) :
			LayoutSizeUtils.computeSize(styleDirectives, WidthDirective.class, sizingContext);
	}

	public float getBasisSize(FlexDirection flexDirection) {
		Optional<FlexBasisDirective> flexBasisDirective = styleDirectives.getDirectiveOrEmpty(FlexBasisDirective.class);
		if (flexBasisDirective.isEmpty() || flexBasisDirective.get().isAuto()) {
			return getMainSize(flexDirection);
		}
		return LayoutSizeUtils.computeSize(styleDirectives, FlexBasisDirective.class, sizingContext);
	}

}
