package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;

public final class FlexUtils {
	
	private FlexUtils() {}

	public static LocalRenderContext createChildRenderContext(FlexItem flexItem, AbsoluteSize preferredSize, LocalRenderContext localRenderContext) {
		return LocalRenderContext.create(
			preferredSize,
			localRenderContext.getParentFontMetrics(),
			new ContextSwitch[0]);
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
