package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.ChildrenContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;

public final class WebBoxGeneratorUtil {
	
	private WebBoxGeneratorUtil() {}
	
	public static List<Box> generateWebBoxes(Context context, BoxContext boxContext, StyleGenerator styleGenerator) {
		OuterDisplay outerDisplay = styleGenerator
			.getStyleDirectives()
			.getDirectiveOrEmpty(OuterDisplayDirective.class)
			.map(directive -> directive.getOuterDisplay())
			.orElse(OuterDisplay.INLINE);

		switch (outerDisplay) {
		case NONE:
			return List.of();
		case CONTENTS:
			if (context instanceof ChildrenContext childrenContext) {
				return generateContentsBoxes(childrenContext, boxContext, styleGenerator);
			}
			return List.of();
		default:
			return UIPipeline.generateBoxes(context, boxContext, styleGenerator);
		}	
		
	}

	private static List<Box> generateContentsBoxes(ChildrenContext context, BoxContext boxContext, StyleGenerator styleGenerator) {
		// TODO: We still need to respect font size inheritence in contents boxes
		// TODO: Also debug why contents is not being set when in first CSS rule with other properties
		Context[] children = context.children(boxContext.getLookAndFeel());
		StyleGenerator[] childrenStyleGenerators = context.childrenStyleGenerators(styleGenerator, children);
		List<Box> boxes = new ArrayList<>(children.length);
		for (int i = 0; i < children.length; i++) {
			boxes.addAll(generateWebBoxes(children[i], boxContext, childrenStyleGenerators[i]));
		}

		return boxes;
	}

}
