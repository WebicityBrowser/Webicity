package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public interface RenderedUnit {

	Box box();
	
	AbsoluteSize fitSize();

	// Standard implementations

	default UIDisplay<?, ?, ?> display() {
		return box().display();
	}

	default ComponentUI componentUI() {
		return box().componentUI();
	}

	// Legacy

	@Deprecated
	default DirectivePool styleDirectives() {
		return componentUI().styleDirectives();
	}
	
}
