package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public interface SolidLayoutManager {

	Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, SolidBox[] solidChildren);

}
