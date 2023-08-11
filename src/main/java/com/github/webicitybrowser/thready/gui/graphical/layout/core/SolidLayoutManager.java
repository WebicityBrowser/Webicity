package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public interface SolidLayoutManager {

	LayoutResult render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext);

}
