package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public interface Context {
	
	UIDisplay<?, ?, ?> display();

	ComponentUI componentUI();

	default List<Context> children() {
		return List.of();
	};

	// Legacy

	@Deprecated
	default DirectivePool styleDirectives() {
		return componentUI().styleDirectives();
	}

	@Deprecated
	default void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {};

}
