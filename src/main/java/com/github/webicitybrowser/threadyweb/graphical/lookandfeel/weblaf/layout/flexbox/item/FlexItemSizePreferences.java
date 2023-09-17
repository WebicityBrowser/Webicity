package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.item;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.FlexMarginCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.FlexUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutBorderWidthCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutPaddingCalculations;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;

public class FlexItemSizePreferences {
	
	private final DirectivePool styleDirectives;
	private final LayoutSizingContext sizingContext;
	private final float[] margins;
	private final float[] padding;
	private final float[] borders;

	public FlexItemSizePreferences(Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		this.styleDirectives = box.styleDirectives();
		this.margins = FlexMarginCalculations.computeMargins(globalRenderContext, localRenderContext, box);
		this.padding = LayoutPaddingCalculations.computePaddings(globalRenderContext, localRenderContext, box);
		this.borders = LayoutBorderWidthCalculations.computeBorderWidths(globalRenderContext, localRenderContext, box);
		this.sizingContext = LayoutSizeUtils.createLayoutSizingContext(
			styleDirectives,
			isHorizontal -> FlexUtils.createSizeCalculationContext(globalRenderContext, localRenderContext, isHorizontal),
			padding, borders);
	}
	
	public float[] getMargins() {
		return margins;
	}

	public float[] getPadding() {
		return padding;
	}

	public float[] getBorders() {
		return borders;
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
