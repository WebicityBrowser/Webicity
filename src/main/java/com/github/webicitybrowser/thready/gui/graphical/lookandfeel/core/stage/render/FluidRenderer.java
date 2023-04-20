package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public interface FluidRenderer extends Renderer {

	UnitGenerator renderFluid(RenderContext renderContext, ContextSwitch[] switches);
	
	default public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		UnitGenerator generator = renderFluid(renderContext, new ContextSwitch[0]);
		while (!generator.completed()) {
			generator.previewNextUnit().append();
		}
		return generator.getMergedUnits();
	};
	
}
