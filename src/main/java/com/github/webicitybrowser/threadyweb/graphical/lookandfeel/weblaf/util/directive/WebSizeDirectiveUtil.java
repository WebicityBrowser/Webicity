package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MaxHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MinHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public final class WebSizeDirectiveUtil {
	
	private WebSizeDirectiveUtil() {}

	public static SizeCalculation getWidth(DirectivePool directives) {
		  return directives
			.getDirectiveOrEmpty(WidthDirective.class)
			.map(directive -> directive.getWidthCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	 }

	 public static SizeCalculation getMaxWidth(DirectivePool directives) {
		  return directives
			.getDirectiveOrEmpty(MaxWidthDirective.class)
			.map(directive -> directive.getMaxWidthCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	 }

	 public static SizeCalculation getMinWidth(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(MinWidthDirective.class)
			.map(directive -> directive.getMinWidthCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	 }

	 public static SizeCalculation getHeight(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(HeightDirective.class)
			.map(directive -> directive.getHeightCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	}

	public static SizeCalculation getMaxHeight(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(MaxHeightDirective.class)
			.map(directive -> directive.getMaxHeightCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	}

	public static SizeCalculation getMinHeight(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(MinHeightDirective.class)
			.map(directive -> directive.getMinHeightCalculation())
			.orElse(SizeCalculation.SIZE_AUTO);
	}


}
