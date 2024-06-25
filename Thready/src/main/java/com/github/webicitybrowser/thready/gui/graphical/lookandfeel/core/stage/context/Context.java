package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public interface Context {
	
	UIDisplay<?, ?, ?> display();

	ComponentUI componentUI();

	// TODO: This exists to make an architectural change easier
	// but it is intended to be removed in the future
	DirectivePool styleDirectives();

	default List<Context> children() {
		return List.of();
	};

	void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext);

	default InvalidationLevel selectivelyRegenerateStyling(DirectivePool styleDirectives, StyleContext styleContext, List<Class<? extends Directive>> updatedDirectiveTypes) {
		regenerateStyling(styleDirectives, styleContext);
		return InvalidationLevel.BOX;
	}

}
