package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record LayerPaintEntry(RenderedUnit paintableUnit, LocalCompositeContext compositeContext) {
}