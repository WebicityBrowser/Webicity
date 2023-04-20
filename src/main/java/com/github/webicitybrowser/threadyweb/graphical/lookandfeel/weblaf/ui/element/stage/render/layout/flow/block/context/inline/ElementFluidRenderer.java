package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor.LineDimensionConverter;

public final class ElementFluidRenderer {
	
	private ElementFluidRenderer() {}

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		LineDimensionConverter dimensionConverter = new HorizontalLineDimensionConverter();
		LineBoxContainer lines = new LineBoxContainer(renderContext, precomputedInnerSize, dimensionConverter);
		renderChildren(children, lines);
		
		LineBoxContainerResult result = lines.collectRenderResults();
		return new FluidChildrenUnit(result.size(), result.childResults());
	}
	
	private static void renderChildren(Box[] children, LineBoxContainer lines) {
		for (Box child: children) {
			lines.addBox(child);
		}
	}

}
