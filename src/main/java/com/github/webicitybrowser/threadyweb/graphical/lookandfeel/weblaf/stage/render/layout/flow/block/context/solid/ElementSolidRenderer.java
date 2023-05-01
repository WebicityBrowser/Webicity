package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public final class ElementSolidRenderer {
	
	private ElementSolidRenderer() {}

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		return new FlowLayoutManagerImp()
			.render(renderContext, precomputedInnerSize, children);
	}
	
}
