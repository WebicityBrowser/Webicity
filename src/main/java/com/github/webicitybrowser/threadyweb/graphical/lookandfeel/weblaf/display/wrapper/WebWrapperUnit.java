package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record WebWrapperUnit<V extends RenderedUnit>(AbsoluteSize preferredSize, WebWrapperBox<?, V> box, V innerUnit, UIDisplay<?, ?, V> innerDisplay) implements RenderedUnit {

}
