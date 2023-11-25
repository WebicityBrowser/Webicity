package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

/**
 * A box is used for determining how components interact with each other.
 * A box represents a contiguous area of the screen that a component takes up.
 * There are two types of boxes: fluid and solid. Fluid boxes are boxes that
 * are not strictly rectangular, and may "flow" around the page. Solid boxes
 * are boxes that are strictly rectangular. The box hierarchy may be adjusted
 * to account for some unique interactions between boxes.
 */
public interface Box {

	UIDisplay<?, ?, ?> display();
	
	Component owningComponent();

	DirectivePool styleDirectives();

	default boolean isFluid() {
		return false;
	};

	default boolean managesSelf() {
		return true;
	};

	default List<Box> getAdjustedBoxTree() {
		return List.of(this);
	}
	
}
