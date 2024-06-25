package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;

public final class ContainerChildrenBoxGenerator {
	
	private ContainerChildrenBoxGenerator() {}
	
	public static void addChildrenBoxes(
		ContainerContext displayContext, ChildrenBox rootBox, BoxContext boxContext
	) {
		List<ContainerChildEntry> children = displayContext.uiMappingCache().getComputedMappings();
		for (ContainerChildEntry child : children) {
			List<Box> boxesToAdd = UIPipeline.generateBoxes(child.context(), boxContext);
			rootBox.getChildrenTracker().addAllChildren(boxesToAdd);
		}
	}

}
