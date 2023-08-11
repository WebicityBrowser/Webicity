package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;

public final class FlowInlineRenderer {

	private FlowInlineRenderer() {}

	public static RenderedUnitGenerator<InnerDisplayUnit> render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new FlowInlineUnitGenerator(box, globalRenderContext, localRenderContext);
	}

}
