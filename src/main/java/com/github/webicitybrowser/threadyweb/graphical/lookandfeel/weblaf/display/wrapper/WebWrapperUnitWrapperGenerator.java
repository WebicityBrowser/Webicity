package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class WebWrapperUnitWrapperGenerator<V extends RenderedUnit> implements RenderedUnitGenerator<WebWrapperUnit<V>> {
    
    private final BoundRenderedUnitGenerator<V> innerUnitGenerator;
    private final WebWrapperWrapperBox<?, V> wrapperBox;

    public WebWrapperUnitWrapperGenerator(BoundRenderedUnitGenerator<V> innerUnitGenerator, WebWrapperWrapperBox<?, V> wrapperBox) {
        this.innerUnitGenerator = innerUnitGenerator;
        this.wrapperBox = wrapperBox;
    }

    @Override
    public WebWrapperUnit<V> generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
        BoundRenderedUnit<V> innerUnit = innerUnitGenerator.getUnit(g -> g.generateNextUnit(preferredBounds, forceFit));
        if (innerUnit == null) {
            return null;
        }
        return new WebWrapperUnit<>(wrapperBox, innerUnit);
    }

    @Override
    public boolean completed() {
        return innerUnitGenerator.getRaw().completed();
    }

}
