package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public record TextBox(UIDisplay<?, ?, ?> display, TextComponent owningComponent, DirectivePool styleDirectives) implements Box {

	@Override
	public boolean isFluid() {
		return true;
	}
	
	public String getText() {
		return owningComponent.getText();
	}
	
}
