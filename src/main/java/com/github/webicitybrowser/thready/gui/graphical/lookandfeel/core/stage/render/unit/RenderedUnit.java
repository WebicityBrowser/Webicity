package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;

/**
 * A rendered unit is the result of rendering a box.
 * It has a minimum "fit" size, which is the smallest size
 * that inner content can comfortably fit in. A rendered box's
 * primary job is to remember layout constraints that would
 * affect positioning or sizes.
 */
public interface RenderedUnit {

	UIDisplay<?, ?, ?> display();
	
	AbsoluteSize fitSize();

	DirectivePool styleDirectives();
	
}
