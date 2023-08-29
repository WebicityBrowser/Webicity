package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record SimpleWrapperUnit<V extends RenderedUnit>(
	UIDisplay<?, ?, ?> display, AbsoluteSize fitSize, DirectivePool styleDirectives, V childUnit
) implements RenderedUnit {

}
