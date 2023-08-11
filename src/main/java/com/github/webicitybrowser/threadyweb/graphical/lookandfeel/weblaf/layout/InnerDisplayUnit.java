package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record InnerDisplayUnit(AbsoluteSize preferredSize, ChildLayoutResult[] childLayoutResults) implements RenderedUnit {

}
