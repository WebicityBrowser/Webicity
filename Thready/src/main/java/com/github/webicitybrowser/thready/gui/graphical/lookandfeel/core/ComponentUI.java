package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;


import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleReference;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public interface ComponentUI {
	
	Component getComponent();

	UIDisplay<?, ?, ?> getRootDisplay();
	
	void invalidate(InvalidationLevel level);

	<T extends Directive> StyleReference<T> getStyleReference(Class<T> directiveClass);

	// Stop-gap
	void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext);

	// Legacy

	@Deprecated
	DirectivePool styleDirectives();
	
}
