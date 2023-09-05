package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.item.FlexItem;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlexUtils {
	
	private FlexUtils() {}

	public static LocalRenderContext createChildRenderContext(FlexItem flexItem, AbsoluteSize preferredSize, LocalRenderContext localRenderContext) {
		return LocalRenderContext.create(
			preferredSize,
			localRenderContext.getParentFontMetrics(),
			new ContextSwitch[0]);
	}

	public static SizeCalculationContext createSizeCalculationContext(
		GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, boolean isHorizontal
	) {
		return new SizeCalculationContext(
			localRenderContext.getPreferredSize(),
			globalRenderContext.viewportSize(),
			localRenderContext.getParentFontMetrics(),
			globalRenderContext.rootFontMetrics(),
			isHorizontal);
	}

	public static FlexDirection getFlexDirection(Box box) {
		return box
			.styleDirectives()
			.getDirectiveOrEmpty(FlexDirectionDirective.class)
			.map(FlexDirectionDirective::getFlexDirection)
			.orElse(FlexDirection.ROW);
	}

	public static FlexWrap getFlexWrap(Box parentBox) {
		return parentBox
			.styleDirectives()
			.getDirectiveOrEmpty(FlexWrapDirective.class)
			.map(FlexWrapDirective::getFlexWrap)
			.orElse(FlexWrap.NOWRAP);
	}

	public static FlexJustifyContent getFlexJustifyContent(Box parentBox) {
		return parentBox
			.styleDirectives()
			.getDirectiveOrEmpty(FlexJustifyContentDirective.class)
			.map(FlexJustifyContentDirective::getJustifyContent)
			.orElse(FlexJustifyContent.FLEX_START);
	}

}
