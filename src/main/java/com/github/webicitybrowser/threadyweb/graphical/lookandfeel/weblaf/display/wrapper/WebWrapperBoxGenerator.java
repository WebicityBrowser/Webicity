package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.WrapperBox;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;

public final class WebWrapperBoxGenerator {

	private WebWrapperBoxGenerator() {}
	
	public static List<WebWrapperBox> generateBoxes(Component owningComponent, DirectivePool directives, Supplier<List<BoundBox<?, ?>>> defaultBoxGenerator) {
		OuterDisplay outerDisplay = WebDirectiveUtil.getOuterDisplay(directives);
		switch (outerDisplay) {
		case CONTENTS:
			// TODO: Obtain the children boxes in a more reliable way
			List<BoundBox<?, ?>> children = defaultBoxGenerator
				.get()
				.stream()
				.map(box -> {
					if (box.getRaw() instanceof WrapperBox wrapperBox) {
						return wrapperBox.innerMostBox();
					} else {
						return box;
					}
				})
				.filter(box -> box.getRaw() instanceof ChildrenBox)
				.flatMap(box -> ((ChildrenBox) box.getRaw()).getChildrenTracker().getChildren().stream())
				.flatMap(box -> box.getAdjustedBoxTree().stream())
				.toList();

			return List.of(new WebWrapperContentsBox(owningComponent, directives, children));

		case NONE:
			return List.of();

		default:
			List<BoundBox<?, ?>> innerBoxes = defaultBoxGenerator.get();
			List<WebWrapperBox> wrapperBoxes = new ArrayList<>(innerBoxes.size());
			for (BoundBox<?, ?> innerBox : innerBoxes) {
				wrapperBoxes.add(new WebWrapperWrapperBox<>(owningComponent, directives, innerBox));
			}
			return wrapperBoxes;
		}
	}
	
}
