package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;

public record FrameBox(FrameComponent owningComponent, UIDisplay<?, ?, ?> display, FrameContext displayContext) implements Box {

	@Override
	public DirectivePool styleDirectives() {
		return displayContext.styleDirectives();
	}

}
