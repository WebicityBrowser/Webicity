package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.contents;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record ContentsUnit(BoundRenderedUnit<?> innerUnit) implements RenderedUnit {

    @Override
    public AbsoluteSize preferredSize() {
        return innerUnit.getRaw().preferredSize();
    }
    
}
