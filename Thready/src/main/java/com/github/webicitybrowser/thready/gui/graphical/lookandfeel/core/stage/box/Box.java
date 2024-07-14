package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box;

import java.util.List;
import java.util.Optional;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface Box {

	Context displayContext();

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

	// Standard implementations

	default Component owningComponent() {
		return componentUI().getComponent();
	}

	default ComponentUI componentUI() {
		return displayContext().componentUI();
	}

	default UIDisplay<?, ?, ?> display() {
		return displayContext().display();
	}

	// Legacy

	@Deprecated
	default DirectivePool styleDirectives() {
		return componentUI().styleDirectives();
	}
	
}
