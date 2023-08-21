package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record InnerDisplayUnit(UIDisplay<?, ?, ?> display, AbsoluteSize preferredSize, ChildLayoutResult[] childLayoutResults) implements RenderedUnit {

}
