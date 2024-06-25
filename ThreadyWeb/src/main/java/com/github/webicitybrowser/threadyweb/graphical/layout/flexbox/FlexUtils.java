package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;

public final class FlexUtils {
	
	private FlexUtils() {}

	public static FlexDirection getFlexDirection(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(FlexDirectionDirective.class)
			.map(FlexDirectionDirective::getFlexDirection)
			.orElse(FlexDirection.ROW);
	}

	public static FlexWrap getFlexWrap(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(FlexWrapDirective.class)
			.map(FlexWrapDirective::getFlexWrap)
			.orElse(FlexWrap.NOWRAP);
	}

	public static FlexJustifyContent getFlexJustifyContent(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(FlexJustifyContentDirective.class)
			.map(FlexJustifyContentDirective::getJustifyContent)
			.orElse(FlexJustifyContent.FLEX_START);
	}

}
