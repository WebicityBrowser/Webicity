package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record SimpleWrapperUnit<T extends RenderedUnit>(AbsoluteSize preferredSize, DirectivePool styleDirectives, T childUnit) implements RenderedUnit {

}
