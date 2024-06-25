package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;

public final class WebBoxGeneratorUtil {
	
	private WebBoxGeneratorUtil() {}
	
	public static List<Box> generateWebBoxes(Context context, BoxContext boxContext) {
		OuterDisplay outerDisplay = context
			.styleDirectives()
			.getDirectiveOrEmpty(OuterDisplayDirective.class)
			.map(directive -> directive.getOuterDisplay())
			.orElse(OuterDisplay.INLINE);

		switch (outerDisplay) {
		case NONE:
			return List.of();
		case CONTENTS:
			return generateContentsBoxes(context, boxContext);
		default:
			return UIPipeline.generateBoxes(context, boxContext);
		}	
		
	}

	private static List<Box> generateContentsBoxes(Context context, BoxContext boxContext) {
		List<Context> children = context.children();
		if (children.isEmpty()) {
			return List.of();
		}

		List<Box> boxes = new ArrayList<>(children.size());
		for (Context child : children) {
			boxes.addAll(generateWebBoxes(child, boxContext));
		}

		return boxes;
	}

}
