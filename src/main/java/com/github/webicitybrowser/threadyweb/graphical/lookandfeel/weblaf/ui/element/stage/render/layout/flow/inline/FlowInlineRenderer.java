package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public class FlowInlineRenderer implements FluidRenderer {

	private Box[] children;

	public FlowInlineRenderer(Box box, Box[] children) {
		this.children = children;
	}

	@Override
	public UnitGenerator renderFluid(RenderContext renderContext) {
		return new FlowInlineUnitGenerator(children, renderContext);
	}

	

}
