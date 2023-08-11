package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public interface InnerDisplayLayout {

	RenderedUnitGenerator<InnerDisplayUnit> renderBox(ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext);
	
}
