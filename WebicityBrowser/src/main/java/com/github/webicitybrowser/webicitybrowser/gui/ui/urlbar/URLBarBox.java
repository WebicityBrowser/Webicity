package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record URLBarBox(Component owningComponent, UIDisplay<?, ?, ?> display, URLBarContext displayContext) implements Box {

	@Override
	public DirectivePool styleDirectives() {
		return displayContext.styleDirectives();
	}

}
