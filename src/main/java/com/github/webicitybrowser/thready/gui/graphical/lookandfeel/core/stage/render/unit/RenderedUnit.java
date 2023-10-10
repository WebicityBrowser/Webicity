package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;

public interface RenderedUnit {

	UIDisplay<?, ?, ?> display();
	
	AbsoluteSize fitSize();

	DirectivePool styleDirectives();
	
}
