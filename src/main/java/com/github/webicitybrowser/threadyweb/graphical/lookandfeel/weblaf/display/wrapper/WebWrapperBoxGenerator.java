package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;

public final class WebWrapperBoxGenerator {

	private WebWrapperBoxGenerator() {}
	
	public static <T extends Context> List<WebWrapperBox> generateBoxes(
		WebWrapperContext<T> displayContext, DirectivePool directives, Supplier<List<Box>> defaultBoxGenerator
	) {
		OuterDisplay outerDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		Component owningComponent = displayContext.componentUI().getComponent();
		UIDisplay<?, ?, ?> display = displayContext.display();

		switch (outerDisplay) {
		case CONTENTS:
			return getContents(displayContext, directives, defaultBoxGenerator);

		case NONE:
			return List.of();

		default:
			List<Box> innerBoxes = defaultBoxGenerator.get();
			List<WebWrapperBox> wrapperBoxes = new ArrayList<>(innerBoxes.size());
			for (Box innerBox : innerBoxes) {
				wrapperBoxes.add(new WebWrapperWrapperBox(display, owningComponent, directives, innerBox));
			}
			return wrapperBoxes;
		}
	}

	private static <T extends Context> List<WebWrapperBox> getContents(
		WebWrapperContext<T> displayContext, DirectivePool directives, Supplier<List<Box>> defaultBoxGenerator
	) {
		Component owningComponent = displayContext.componentUI().getComponent();
		UIDisplay<?, ?, ?> display = displayContext.display();

		// TODO: Obtain the children boxes in a more reliable way
		List<Box> children = defaultBoxGenerator
			.get()
			.stream()
			.map(box -> {
				if (box instanceof WrapperBox wrapperBox) {
					return wrapperBox.innerMostBox();
				} else {
					return box;
				}
			})
			.filter(box -> box instanceof ChildrenBox)
			.flatMap(box -> ((ChildrenBox) box).getChildrenTracker().getChildren().stream())
			.flatMap(box -> box.getAdjustedBoxTree().stream())
			.toList();

		return List.of(new WebWrapperContentsBox(display, owningComponent, directives, children));
	}
	
}
