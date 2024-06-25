package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record BreakUnit(UIDisplay<?, ?, ?> display, AbsoluteSize fitSize, DirectivePool styleDirectives) implements RenderedUnit {

}
