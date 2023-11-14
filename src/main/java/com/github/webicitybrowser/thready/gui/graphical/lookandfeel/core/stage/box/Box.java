package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

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

	default Optional<ReplacedInfo> replacedInfo() {
		return Optional.empty();
	}

	default List<Box> getAdjustedBoxTree() {
		return List.of(this);
	}
	
}
