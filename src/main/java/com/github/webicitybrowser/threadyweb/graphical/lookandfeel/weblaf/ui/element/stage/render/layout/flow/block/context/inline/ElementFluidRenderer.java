package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.HorizontalCursorTracker;

public final class ElementFluidRenderer {
	
	private ElementFluidRenderer() {}

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		FluidLines lines = new FluidLines(renderContext, precomputedInnerSize, new HorizontalCursorTracker());
		renderChildren(children, lines);
		
		AbsoluteSize computedSize = lines.computeTotalSize();
		return new FluidChildrenUnit(computedSize, lines.getRenderResults());
	}
	
	private static void renderChildren(Box[] children, FluidLines lines) {
		for (Box child: children) {
			lines.addBox(child);
		}
	}

}
