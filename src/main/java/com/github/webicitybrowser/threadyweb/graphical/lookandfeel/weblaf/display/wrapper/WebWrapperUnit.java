package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record WebWrapperUnit<V extends RenderedUnit>(WebWrapperWrapperBox<?, V> box, BoundRenderedUnit<V> innerUnit) implements RenderedUnit {

    @Override
    public AbsoluteSize preferredSize() {
        return innerUnit.getRaw().preferredSize();
    }

}
